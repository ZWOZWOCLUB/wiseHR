package com.wisehr.wisehr.payment.service;


import com.wisehr.wisehr.payment.dto.*;
import com.wisehr.wisehr.payment.entity.*;
import com.wisehr.wisehr.payment.repository.*;
import com.wisehr.wisehr.util.ApprovalFileUtils;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ApprovalService {

    private final ApprovalCompleteRepository approvalCompleteRepository;
    private final ApprovalRepository approvalRepository;
    private final ApprovalAnnualRepository approvalAnnualRepository;
    private final ApprovalPerArmRepository approvalPerArmRepository;
    private final ApprovalVHRepository approvalVHRepository;
    private final EditCommuteRepository editCommuteRepository;
    private final EditScheduleRepository editScheduleRepository;
    private final ApprovalRetiredRepository approvalRetiredRepository;
    private final ModelMapper modelMapper;
    private final ApprovalFileUtils fileUtils;

    // 이미지 저장 위치 및 응답 할 이미지 주소 (여기 뒤에 /{memCode} 넣으면 됩니다!!
    // 위아래 둘 다!! 동적으로)
    @Value("src/main/resources/static/")
    private String IMAGE_DIR;

    @Value("http://localhost:8001/")
    private String IMAGE_URL;

    @Autowired
    public ApprovalService(ApprovalCompleteRepository approvalCompleteRepository, ApprovalRepository approvalRepository, ApprovalAnnualRepository approvalAnnualRepository, ApprovalPerArmRepository approvalPerArmRepository, ApprovalVHRepository approvalVHRepository, EditCommuteRepository editCommuteRepository, EditScheduleRepository editScheduleRepository, ApprovalRetiredRepository approvalRetiredRepository, ModelMapper modelMapper, ApprovalFileUtils fileUtils) {
        this.approvalCompleteRepository = approvalCompleteRepository;
        this.approvalRepository = approvalRepository;
        this.approvalAnnualRepository = approvalAnnualRepository;
        this.approvalPerArmRepository = approvalPerArmRepository;
        this.approvalVHRepository = approvalVHRepository;
        this.editCommuteRepository = editCommuteRepository;
        this.editScheduleRepository = editScheduleRepository;
        this.approvalRetiredRepository = approvalRetiredRepository;
        this.modelMapper = modelMapper;
        this.fileUtils = fileUtils;
    }



    // 받은 결재 #결제 완료 및
    public List<ApprovalCompleteDTO> selectReqPayment(Long memCode) {
        log.info("select 받은결재 memCode : " + memCode);
        log.info("memCode : " + memCode.getClass());


        List<ApprovalComplete> paymentList = approvalCompleteRepository.findByApprovalMemberMemCode(memCode);

        log.info("paymentList : " + paymentList);

        List<ApprovalCompleteDTO> payment = paymentList.stream()
                .map(paymt -> modelMapper.map(paymt, ApprovalCompleteDTO.class))
                .collect(Collectors.toList());

        log.info("받은 결재 완료 payment : " + payment);

        return payment;
    }

    //보낸결재
    public List<ApprovalCompleteDTO> selectRecPayment(Long memCode) {
        log.info("reqPayment Service Start : " + memCode);

        List<ApprovalComplete> paymentList = approvalCompleteRepository.findByApprovalApprovalMemberMemCode(memCode);

        log.info("log paymentList : " + paymentList);

        List<ApprovalCompleteDTO> payment = paymentList.stream()
                .map(paymt -> modelMapper.map(paymt, ApprovalCompleteDTO.class))
                .collect(Collectors.toList());

        log.info("보낸 결재 완료 payment : " + payment);

        return payment;
    }

    @Transactional
    public String submitCommute(EditCommuteDTO edit, MultipartFile approvalFile) {

        int result = 0;
        try {
            Approval app = modelMapper.map(edit.getApproval(), Approval.class);

            log.info("app : " + app);

            Approval aps = approvalRepository.save(app);

            log.info("aps : " + aps);

            edit.getApproval().setPayCode(aps.getPayCode());

            log.info("결재 완료 + edit : " + edit);

            editCommuteRepository.save(modelMapper.map(edit, EditCommute.class));

            log.info("출퇴근 정정 완료 ");

            if (approvalFile != null){

                fileUtils.fileClear(edit.getApproval(), approvalFile);

                log.info("출퇴근 첨부파일 성공");
            }

            ApprovalCompleteDTO ac = new ApprovalCompleteDTO();

            ac.setApproval(edit.getApproval());
            ac.setApprovalMember(edit.getApproval().getApprovalMember());
            ac.setAppState("대기");

            log.info("ac : " + ac);

            ApprovalComplete acc = modelMapper.map(ac, ApprovalComplete.class);

            log.info("acc : " + acc);

            approvalCompleteRepository.save(acc);

            log.info("결제완료 쪽 완성");

            result = 1;

        } catch (Exception e) {
            e.printStackTrace();
            log.info("실패~");
        }


        return (result > 0) ? "출퇴근 정정 성공" : "실패~";
    }

    // 연차결재상신
    @Transactional
    public String submitAnnual(ApprovalAnnualDTO annual, MultipartFile approvalFile) {

        log.info("===== attchment start : " + approvalFile);
        log.info("-===== paymentAnnual : " + annual);

        int result = 0;


            try {
                Approval approval = modelMapper.map(annual.getApproval(), Approval.class);

                log.info("approval : " + approval);

                Approval app = approvalRepository.save(approval);

                log.info("결재 성공");

                annual.getApproval().setPayCode(app.getPayCode());


                ApprovalAnnual atcment = modelMapper.map(annual, ApprovalAnnual.class);

                log.info("atcment : " + atcment);

                approvalAnnualRepository.save(atcment);

                log.info("연차 성공");

                if (approvalFile != null) {

                    fileUtils.fileClear(annual.getApproval(), approvalFile);

                    log.info("첫 메서드 만들기 성공!");
                }


                ApprovalCompleteDTO ac = new ApprovalCompleteDTO();

                ac.setApproval(annual.getApproval());
                ac.setApprovalMember(annual.getApproval().getApprovalMember());
                ac.setAppState("대기");

                log.info("ac : " + ac);

                ApprovalComplete acc = modelMapper.map(ac, ApprovalComplete.class);

                log.info("acc : " + acc);

                approvalCompleteRepository.save(acc);

                log.info("결제완료 쪽 완성");


                // Repository를 통해 DB에 저장!

                result = 1;
            } catch (Exception e) {
                log.info("실패~");
                e.printStackTrace();
            }



            return (result > 0) ? "성공" : "실패";
    }

    @Transactional
    public String completeApproval(ApprovalCompleteDTO approval) {

        log.info("complete Service Start approval : " + approval);

        int result = 0 ;

        ApprovalComplete ac = approvalCompleteRepository.findById(approval.getAppCode()).get();

        log.info("acE : " + ac);

        try {
            // 결재 승인과 동시에 결재 기안자에게 보낼 알람 생성

            LocalDateTime now = LocalDateTime.now();

            ApprovalPerAlarm apa = new ApprovalPerAlarm();
            apa.setPerArmCheckStatus("N");
            apa.setPerArmDateTime(now);
            apa.setReceiveAramMember(modelMapper.map(ac.getApproval().getApprovalMember(),ReceiveAramMember.class));

            log.info("apa : " + apa);

            approvalPerArmRepository.save(apa);

            ApprovalComplete acc = modelMapper.map(approval, ApprovalComplete.class);

            log.info("acc : " + acc);

            acc.setApproval(ac.getApproval());
            acc.setApprovalMember(ac.getApprovalMember());
            acc.setPerArm(apa);

            log.info("acc C : " + acc);

            approvalCompleteRepository.save(acc);


            log.info("결재 승인 완료");

            if (acc.getApproval().getPayKind().contains("연차") && acc.getAppState().equals("승인")){

                ApprovalAnnual aa = approvalAnnualRepository.findByApprovalPayCode(ac.getApproval().getPayCode());
                log.info("aa : " + aa);

                LocalDate startDate = aa.getVacStartDate().toLocalDate();
                LocalDate endDate = aa.getVacEndDate().toLocalDate();

                log.info("startDate : " + startDate);
                log.info("endDate : " + endDate);

                long spendDay = ChronoUnit.DAYS.between(startDate, endDate);

                log.info("spend : " + spendDay);

                int days = (int) Math.abs(spendDay) + 1 ;     // 둘이 같은 날이면 1일로 하도록 +1

                log.info("days : " + days);

                ApprovalVH av = new ApprovalVH();
                av.setVhiSpend(days);
                av.setAppCode(acc);
                av.setApprovalMember(acc.getApproval().getApprovalMember());

                log.info("av : " + av);

                approvalVHRepository.save(av);

                log.info("연차이력까지 성공");

            }

            result = 1;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        return (result > 0 ) ? "성공!"  : "실패" ;
    }


    public String submitSchedule(EditScheduleDTO schedule, MultipartFile approvalFile) {

        int result = 0;

        try {
            Approval app = modelMapper.map(schedule.getApproval(), Approval.class);

            log.info("app : " + app);

            Approval aps = approvalRepository.save(app);

            log.info("aps : " + aps);

            schedule.getApproval().setPayCode(aps.getPayCode());

            log.info("결재 완료 + edit : " + schedule);

            editScheduleRepository.save(modelMapper.map(schedule, EditSchedule.class));

            log.info("스케줄 정정 완료 ");

            if (approvalFile != null){

                fileUtils.fileClear(schedule.getApproval(), approvalFile);

                log.info("스케줄 첨부파일 성공");
            }

            ApprovalCompleteDTO ac = new ApprovalCompleteDTO();

            ac.setApproval(schedule.getApproval());
            ac.setApprovalMember(schedule.getApproval().getApprovalMember());
            ac.setAppState("대기");

            log.info("ac : " + ac);

            ApprovalComplete acc = modelMapper.map(ac, ApprovalComplete.class);

            log.info("acc : " + acc);

            approvalCompleteRepository.save(acc);

            log.info("결제완료 쪽 완성");

            result = 1;


        }catch (Exception e){
            e.printStackTrace();
            log.info("실패");
        }


        return (result > 0) ? "스케줄 정정 최종 성공 " : "최종 실패";
    }
}