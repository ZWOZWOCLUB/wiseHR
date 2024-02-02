package com.wisehr.wisehr.payment.service;


import com.wisehr.wisehr.payment.dto.*;
import com.wisehr.wisehr.payment.entity.*;
import com.wisehr.wisehr.payment.repository.*;
import com.wisehr.wisehr.util.ApprovalUtils;
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
import java.util.Map;
import java.util.Optional;
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
    private final ApprovalReqDocumentRepository approvalReqDocumentRepository;
    private final ApprovalMemberRepository approvalMemberRepository;
    private final ApproverProxyRepository approverProxyRepository;
    private final ModelMapper modelMapper;
    private final ApprovalUtils fileUtils;

    // 이미지 저장 위치 및 응답 할 이미지 주소 (여기 뒤에 /{memCode} 넣으면 됩니다!!
    // 위아래 둘 다!! 동적으로)
    @Value("src/main/resources/static/")
    private String IMAGE_DIR;

    @Value("http://localhost:8001/")
    private String IMAGE_URL;

    @Autowired
    public ApprovalService(ApprovalCompleteRepository approvalCompleteRepository, ApprovalRepository approvalRepository, ApprovalAnnualRepository approvalAnnualRepository, ApprovalPerArmRepository approvalPerArmRepository, ApprovalVHRepository approvalVHRepository, EditCommuteRepository editCommuteRepository, EditScheduleRepository editScheduleRepository, ApprovalRetiredRepository approvalRetiredRepository, ApprovalReqDocumentRepository approvalReqDocumentRepository, ApprovalMemberRepository approvalMemberRepository, ApproverProxyRepository approverProxyRepository, ModelMapper modelMapper, ApprovalUtils fileUtils) {
        this.approvalCompleteRepository = approvalCompleteRepository;
        this.approvalRepository = approvalRepository;
        this.approvalAnnualRepository = approvalAnnualRepository;
        this.approvalPerArmRepository = approvalPerArmRepository;
        this.approvalVHRepository = approvalVHRepository;
        this.editCommuteRepository = editCommuteRepository;
        this.editScheduleRepository = editScheduleRepository;
        this.approvalRetiredRepository = approvalRetiredRepository;
        this.approvalReqDocumentRepository = approvalReqDocumentRepository;
        this.approvalMemberRepository = approvalMemberRepository;
        this.approverProxyRepository = approverProxyRepository;
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

            ApprovalMember depRole = fileUtils.roleMember(edit.getApproval().getApprovalMember().getMemCode());

            ac.setApprovalMember(modelMapper.map(depRole, ApprovalMemberDTO.class));
            ac.setApproval(edit.getApproval());
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

    // 퇴직 요청 상신
    public String submitRetired(ApprovalRetiredDTO retired, MultipartFile approvalFile) {

        int result = 0;

        try {
            Approval app = modelMapper.map(retired.getApproval(), Approval.class);

            log.info("app : " + app);

            Approval aps = approvalRepository.save(app);

            log.info("aps : " + aps);

            retired.getApproval().setPayCode(aps.getPayCode());

            log.info("결재 완료 + retired : " + retired);

            approvalRetiredRepository.save(modelMapper.map(retired, ApprovalRetired.class));

            log.info("퇴직 요청 완료 ");

            if (approvalFile != null){

                fileUtils.fileClear(retired.getApproval(), approvalFile);

                log.info("퇴직 첨부파일 성공");
            }



            ApprovalCompleteDTO ac = new ApprovalCompleteDTO();

            ApprovalMember depRole = fileUtils.roleMember(retired.getApproval().getApprovalMember().getMemCode());

            ac.setApprovalMember(modelMapper.map(depRole, ApprovalMemberDTO.class));
            ac.setApproval(retired.getApproval());
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


        return (result > 0) ? "퇴직 요청 성공" : "실패~";
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

                ApprovalMember depRole = fileUtils.roleMember(annual.getApproval().getApprovalMember().getMemCode());

                ApprovalCompleteDTO ac = new ApprovalCompleteDTO();

                ac.setApprovalMember(modelMapper.map(depRole, ApprovalMemberDTO.class));
                ac.setApproval(annual.getApproval());
                ac.setAppState("대기");

                log.info("ac : " + ac);

                ApprovalComplete acc = modelMapper.map(ac, ApprovalComplete.class);

                log.info("acc : " + acc);

                approvalCompleteRepository.save(acc);

                log.info("결제완료 쪽 완성");

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

            } else if (acc.getApproval().getPayKind().contains("스케줄") && acc.getAppState().equals("승인")) {

                EditSchedule es = editScheduleRepository.findByApprovalPayCode(acc.getApproval().getPayCode());

                LocalDate startDate = es.getEshStartDate().toLocalDate();
                LocalDate endDate = es.getEshEndDate().toLocalDate();

                log.info("startDate : " + startDate);
                log.info("endDate : " + endDate);

                long spendDay = ChronoUnit.DAYS.between(startDate, endDate);

                log.info("spend : " + spendDay);

                int days = (int) Math.abs(spendDay) + 1 ;

                if (days == 1 ){



                }


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

            ApprovalMember depRole = fileUtils.roleMember(schedule.getApproval().getApprovalMember().getMemCode());

            ac.setApprovalMember(modelMapper.map(depRole, ApprovalMemberDTO.class));
            ac.setApproval(schedule.getApproval());
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

    public String submitReqDocumnet(ApprovalReqDocumentDTO reqDocument, MultipartFile approvalFile) {

        int result = 0;

        try {
            Approval app = modelMapper.map(reqDocument.getApproval(), Approval.class);

            log.info("app : " + app);

            Approval aps = approvalRepository.save(app);

            log.info("aps : " + aps);

            reqDocument.getApproval().setPayCode(aps.getPayCode());

            log.info("결재 완료 + edit : " + reqDocument);

            approvalReqDocumentRepository.save(modelMapper.map(reqDocument, ApprovalReqDocument.class));

            log.info("서류 요청 완료 ");

            if (approvalFile != null){

                fileUtils.fileClear(reqDocument.getApproval(), approvalFile);

                log.info("서류 요청 첨부파일 성공");
            }

            ApprovalCompleteDTO ac = new ApprovalCompleteDTO();

            ApprovalMember depRole = fileUtils.roleMember(reqDocument.getApproval().getApprovalMember().getMemCode());

            ac.setApprovalMember(modelMapper.map(depRole, ApprovalMemberDTO.class));
            ac.setApproval(reqDocument.getApproval());
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


        return (result > 0) ? "서류 요청 최종 성공 " : "최종 실패";
    }

    public String updateRole(ApproverProxyDTO proxy) {

        log.info("전결자 서비스 시작 ");

        int result = 0;

        try {

            // 권한을 업데이트 해줄 전결자의 정보를 가져온다
            ApprovalMember memRole = approvalMemberRepository.findByMemCode(proxy.getProMember().getMemCode());

            log.info("memRole : " + memRole);

            proxy.setProMemRole(memRole.getMemRole());

            approverProxyRepository.save(modelMapper.map(proxy, ApproverProxy.class));

            log.info("전결자 지정 데이터 등록 성공");

            // 기존 권한자의 권한값을 가져오기 위해서 권한자 정보를 가져오는 단계
            ApprovalMember roleMemCode = approvalMemberRepository.findByMemCode(proxy.getRoleMember().getMemCode());

            log.info("roleMemCode : " + roleMemCode);

            memRole.setMemRole(roleMemCode.getMemRole());

            log.info("memRole : " + memRole);

            approvalMemberRepository.save(memRole);

            log.info("전결자 지정 완료");

            result = 1;

        }catch (Exception e){
            e.printStackTrace();
            log.info("실패~");
        }

        return (result > 0 ) ? "성공" : "실패";
    }

    // 전결자 해제 및 기존 권한으로 복귀
    public String recoveryRole(Map<String , Long> requestBody) {

        log.info("전결자 복구 서비스 시작 ");

        int result = 0;

        try {

            ApproverProxy aproxy = approverProxyRepository.findFirstByRoleMemberMemCodeOrderByProEndDateDesc(requestBody.get("memCode"));

            log.info("aproxy : " + aproxy);

            ApprovalMember apm = approvalMemberRepository.findByMemCode(aproxy.getProMember().getMemCode());

            apm.setMemRole(aproxy.getProMemRole());

            log.info("apm : " + apm);

            approvalMemberRepository.save(apm);

            result = 1;

        }catch (Exception e){
            e.printStackTrace();
            log.info("실패~");
        }

        return (result > 0 ) ? "성공" : "실패";
    }
}
