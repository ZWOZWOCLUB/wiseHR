package com.wisehr.wisehr.approval.service;


import com.wisehr.wisehr.approval.dto.*;
import com.wisehr.wisehr.approval.entity.*;
import com.wisehr.wisehr.approval.repository.*;
import com.wisehr.wisehr.attendance.entity.Attendance;
import com.wisehr.wisehr.attendance.repository.AttendanceRepository;
import com.wisehr.wisehr.mypage.entity.MPHoldVacation;
import com.wisehr.wisehr.mypage.repository.MPHoldVacationRepository;
import com.wisehr.wisehr.schedule.entity.ScheduleEtcPattern;
import com.wisehr.wisehr.schedule.repository.ScheduleEtcPatternRepository;
import com.wisehr.wisehr.schedule.repository.ScheduleWorkPatternRepository;
import com.wisehr.wisehr.util.ApprovalUtils;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
    private final ScheduleEtcPatternRepository scheduleEtcPatternRepository;
    private final ScheduleWorkPatternRepository scheduleWorkPatternRepository;
    private final ApprovalScheduleRepository approvalScheduleRepository;
    private final ApprovalPatternDayRepository approvalPatternDayRepository;
    private final AttendanceRepository attendanceRepository;
    private final ApprovalAttachmentRepository approvalAttachmentRepository;
    private final MPHoldVacationRepository holdVacationRepository;
    private final ApprovalMember2Repository approvalMember2Repository;
    private final AppSchMemberRepository approvalScheduleMemberRepository;

    private final ModelMapper modelMapper;
    private final ApprovalUtils fileUtils;

    // 이미지 저장 위치 및 응답 할 이미지 주소 (여기 뒤에 /{memCode} 넣으면 됩니다!!
    // 위아래 둘 다!! 동적으로)
    @Value("src/main/resources/static/")
    private String IMAGE_DIR;

    @Value("http://localhost:8001/")
    private String IMAGE_URL;

    @Autowired
    public ApprovalService(ApprovalCompleteRepository approvalCompleteRepository, ApprovalRepository approvalRepository, ApprovalAnnualRepository approvalAnnualRepository, ApprovalPerArmRepository approvalPerArmRepository, ApprovalVHRepository approvalVHRepository, EditCommuteRepository editCommuteRepository, EditScheduleRepository editScheduleRepository, ApprovalRetiredRepository approvalRetiredRepository, ApprovalReqDocumentRepository approvalReqDocumentRepository, ApprovalMemberRepository approvalMemberRepository, ApproverProxyRepository approverProxyRepository, ScheduleEtcPatternRepository scheduleEtcPatternRepository, ScheduleWorkPatternRepository scheduleWorkPatternRepository, ApprovalScheduleRepository approvalScheduleRepository, ApprovalPatternDayRepository approvalPatternDayRepository, AttendanceRepository attendanceRepository, ApprovalAttachmentRepository approvalAttachmentRepository, MPHoldVacationRepository holdVacationRepository, ApprovalMember2Repository approvalMember2Repository, AppSchMemberRepository approvalScheduleMemberRepository, ModelMapper modelMapper, ApprovalUtils fileUtils) {
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
        this.scheduleEtcPatternRepository = scheduleEtcPatternRepository;
        this.scheduleWorkPatternRepository = scheduleWorkPatternRepository;
        this.approvalScheduleRepository = approvalScheduleRepository;
        this.approvalPatternDayRepository = approvalPatternDayRepository;
        this.attendanceRepository = attendanceRepository;
        this.approvalAttachmentRepository = approvalAttachmentRepository;
        this.holdVacationRepository = holdVacationRepository;
        this.approvalMember2Repository = approvalMember2Repository;
        this.approvalScheduleMemberRepository = approvalScheduleMemberRepository;
        this.modelMapper = modelMapper;
        this.fileUtils = fileUtils;
    }



    // 받은 결재 #결제 완료 및
    public List<ApprovalCompleteDTO> selectReqPayment(Long memCode) {
        log.info("select 받은결재 memCode : " + memCode);
        log.info("memCode : " + memCode.getClass());



        List<ApprovalComplete> paymentList = approvalCompleteRepository.findByApprovalMemberMemCodeOrderByAppStateAndApprovalPayDateDesc(memCode);

        log.info("paymentList : " + paymentList);

//        List<ApprovalCompleteDTO> payment =;

//        log.info("받은 결재 완료 payment : " + payment);

        log.info("맞음? ");
        List<ApprovalCompleteDTO> result = paymentList.stream()
                .map(paymt  -> modelMapper.map(paymt, ApprovalCompleteDTO.class))
                .collect(Collectors.toList());
        log.info("------------->" + result);
        log.info("보낸 결재 완료 payment : " + result.get(0));
        return result;



    }

    //보낸결재
    public List<ApprovalCompleteDTO> selectRecPayment(Long memCode) {
        log.info("reqPayment Service Start : " + memCode);


        List<ApprovalComplete> paymentList = approvalCompleteRepository.findByApprovalApprovalMemberMemCodeOrderByAppStateAndApprovalPayDateDesc(memCode);


        log.info("log paymentList : " + paymentList);

        List<ApprovalCompleteDTO> payment = paymentList.stream()
                .map(paymt -> modelMapper.map(paymt, ApprovalCompleteDTO.class))
                .collect(Collectors.toList());



        return payment;
    }

    // 출퇴근 정정 상신
    @Transactional
    public String submitCommute(EditCommute2DTO edit, MultipartFile approvalFile) {

        int result = 0;
        try {
            Approval app = modelMapper.map(edit.getApproval(), Approval.class);

            log.info("app : " + app);

            Approval aps = approvalRepository.save(app);

            log.info("aps : " + aps);

            edit.getApproval().setPayCode(aps.getPayCode());

            log.info("결재 완료 + edit : " + edit);

            EditCommute editCommute = modelMapper.map(edit, EditCommute.class);

            editCommuteRepository.save(editCommute);

            log.info("출퇴근 정정 완료 ");

            if (approvalFile != null){

                fileUtils.fileClear(edit.getApproval(), approvalFile);

                log.info("출퇴근 첨부파일 성공");
            }

            ApprovalCompleteDTO ac = new ApprovalCompleteDTO();


            ac.setApprovalMember(edit.getCMember());
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
    @Transactional
    public String submitRetired(ApprovalRetired2DTO retired, MultipartFile approvalFile) {

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


            ac.setApprovalMember(retired.getCMember());
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
    public String submitAnnual(ApprovalAnnual2DTO annual, MultipartFile approvalFile) {

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

                ac.setApprovalMember(annual.getCMember());
                log.info("여기? ");
                ac.setApproval(annual.getApproval());
                log.info("여기?!");
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

    // 결재 승인 로직
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
            log.info("22");
            apa.setPerArmCheckStatus("N");
            apa.setPerArmDateTime(now);
            log.info("33");
            apa.setReceiveAramMember(modelMapper.map(ac.getApproval().getApprovalMember(),ReceiveAramMember.class));
            log.info("44");

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

                LocalDate startDate = LocalDate.parse(aa.getVacStartDate());
                LocalDate endDate = LocalDate.parse(aa.getVacEndDate());

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
                av.setVhiKind(aa.getVacKind());

                log.info("av : " + av);


                MPHoldVacation holdVacation = holdVacationRepository.findByMemCode((acc.getApproval().getApprovalMember().getMemCode()).intValue());
                log.info("holdVacation == : " + holdVacation);

                if (aa.getVacKind().contains("무급")){
                    log.info("여기서는 연차 개수를 조정하지 않습니다. ");

                }else {

                    log.info("연차 계산 시작 ");

                    if (holdVacation.getVctDeadline() > 0) {   //먼저 소멸예정 연차를 차감하기 위하여 0 이상일 경우로 설정

                        if (holdVacation.getVctDeadline() - days < 0) { // 사용한 연차 개수보다 소멸예정 연차가 적을 경우
                            int spendVacation = -(holdVacation.getVctDeadline() - days);      // 남은걸 양수로 만들고
                            int spendVacation2 = holdVacation.getVctCount() - spendVacation;  // 연차를 마저 빼주는 것
                            log.info("spendVacation : " + spendVacation);
                            log.info("spendVacation2 : " + spendVacation2);

                            if (spendVacation2 < 0) {   // 남은 연차가 부족할 경우

                                acc.setAppState("반려");

                                approvalCompleteRepository.save(acc); // 반려로 처리

                                return "연차가 부족합니다.";
                            }

                            holdVacation.vctDeadline(0);
                            holdVacation.vctCount(spendVacation2);
                            holdVacation.vctAmountSpendVacation(holdVacation.getVctAmountSpendVacation() + days);
                            log.info("holdVacation : ", holdVacation);
                            holdVacationRepository.save(holdVacation);
                        } else {

                            int spendVacation = holdVacation.getVctDeadline() - days;
                            log.info("spendVacation : " + spendVacation);

                            if (spendVacation < 0) {// 연차 부족 반려

                                acc.setAppState("반려");

                                approvalCompleteRepository.save(acc);

                                return "연차가 부족합니다.";
                            }

                            holdVacation.vctDeadline(spendVacation);
                            holdVacation.vctAmountSpendVacation(holdVacation.getVctAmountSpendVacation() + days);
                            holdVacationRepository.save(holdVacation);
                            log.info("holdVacation : ", holdVacation);
                            log.info("연차 소유 누적 까지 성공 ");

                        }
                    }
                }



                approvalVHRepository.save(av);  // 연차이력 저장

                log.info("연차이력까지 성공");

            } else if (acc.getApproval().getPayKind().contains("스케줄") && acc.getAppState().equals("승인")) {

                // 상신한 결재의 종류가 스케줄이면서 승인이면 들어옴

                EditSchedule es = editScheduleRepository.findByApprovalPayCode(acc.getApproval().getPayCode());

                // 상신한 결재의 결재번호를 통해서 결재 값 가져옴

                log.info("es : " + es);

                LocalDate startDate = Date.valueOf(es.getEshOffStartDate()).toLocalDate();
                LocalDate endDate = Date.valueOf(es.getEshOffEndDate()).toLocalDate();
                LocalDate startDate2 = Date.valueOf(es.getEshStartDate()).toLocalDate();
                LocalDate endDate2 = Date.valueOf(es.getEshEndDate()).toLocalDate();

                // 스케줄 정정의 시작날짜와 종료날짜를 구해서  (쉬는날, 일하는날)

                log.info("startDate : " + startDate2);
                log.info("endDate : " + endDate2);

                long spendDay = ChronoUnit.DAYS.between(startDate2, endDate2); // 두 날짜의 간격을 통해서 며칠인지 찾고

                log.info("spend : " + spendDay);

                int days = (int) Math.abs(spendDay) + 1;        // 며칠이 몇일인지 같은날 시작 같은날 종료면 1일이 되도록 구해서

                // 신청한 기한의 날짜 수를 확인해서 배정

                if (days == 1) {

                    int type = 0;
                    ScheduleEtcPattern sep = new ScheduleEtcPattern();
                    sep.etcDate(String.valueOf(startDate));
                    sep.etcKind(String.valueOf(0));
                    sep.memCode(es.getApproval().getApprovalMember().getMemCode().intValue());
                    log.info("sep : " + sep);

                    scheduleEtcPatternRepository.save(sep);

                    log.info("휴일로 설정되면 etc_kind는 0으로 설정됩니다. ");

                    ScheduleEtcPattern sep2 = new ScheduleEtcPattern();
                    sep2.etcDate(String.valueOf(startDate2));
//                    sep.etcKind(es.getEshDateType()); 타입을 박는게 아니라 타입에 맞는 시간 패턴으로 넣어야지
                    switch (es.getEshDateType()) {
                        case "Day":
                            type = 1;
                        case "Evening":
                            type = 2;
                        case "Night":
                            type = 3;
                    }
                    sep2.etcKind(String.valueOf(type));
                    sep2.memCode((es.getApproval().getApprovalMember().getMemCode()).intValue());
                    log.info("sep2 : " + sep2);

                    scheduleEtcPatternRepository.save(sep2);

                    log.info("스케줄 정정 완료! 1일");

                } else if (days > 1) {

                    log.info("스케줄 정정 2일 이상 시작 ");

                    for (int i = 0; i < days; i++) {

                        int type = 0;

                        ScheduleEtcPattern sep = new ScheduleEtcPattern();
                        sep.etcDate(String.valueOf(startDate.plusDays(i)));
                        sep.etcKind(String.valueOf(0));
                        sep.memCode(es.getApproval().getApprovalMember().getMemCode().intValue());
                        log.info("sep : " + sep);

                        scheduleEtcPatternRepository.save(sep);

                        ScheduleEtcPattern sep2 = new ScheduleEtcPattern();
                        sep2.etcDate(String.valueOf(startDate2.plusDays(i)));
//                    sep.etcKind(es.getEshDateType()); 타입을 박는게 아니라 타입에 맞는 시간 패턴으로 넣어야지
                        switch (es.getEshDateType()) {
                            case "Day":
                                type = 1;
                            case "Evening":
                                type = 2;
                            case "Night":
                                type = 3;

                        }
                        sep2.etcKind(String.valueOf(type));
                        sep2.memCode((es.getApproval().getApprovalMember().getMemCode()).intValue());
                        log.info("sep2 : " + sep2);

                        scheduleEtcPatternRepository.save(sep2);

                    }
                }
            }else if (acc.getApproval().getPayKind().contains("퇴직") && acc.getAppState().equals("승인")) {

                    Long retiredMemCode = acc.getApproval().getApprovalMember().getMemCode();
                    log.info("retiredMemCode : " + retiredMemCode);

                    ApprovalMember retiredMember = approvalMemberRepository.findByMemCode(retiredMemCode);
                    log.info("retiredMember : " + retiredMember);

                    ApprovalRetired retired = approvalRetiredRepository.findByApprovalPayCode(acc.getApproval().getPayCode());
                    log.info("----" + String.valueOf(retired.getTirDate()));

                    retiredMember.setMemStatus("Y");
                    retiredMember.setMemEndDate(String.valueOf(retired.getTirDate()));

                    approvalMemberRepository.save(retiredMember);

                } else if (acc.getApproval().getPayKind().contains("출퇴근") && acc.getAppState().equals("승인")) {

                    EditCommute ec = editCommuteRepository.findByApprovalPayCode(acc.getApproval().getPayCode());   // 수정해야할 날짜를 찾기위해 결재 코드 찾기

                    log.info("ec : " + ec);

                    Attendance ad = attendanceRepository.findByAttWorkDateAndAttendanceMemberMemCode(Date.valueOf(ec.getEdiDate()), ec.getApproval().getApprovalMember().getMemCode());
                    // 결재코드에 존재하는 날짜와 사번을 통해서 그 날의 근태상태 가져옴

                    log.info("ad : " + ad );

                    ApprovalSchedule sch = approvalScheduleRepository.findBySchCode(ad.getAttendanceSchedule().getSchCode());       // 그날의 근태가 어떤 근무였는지 알기 위해서 스케줄 값 가져옴

                    log.info("sch : " + sch);

                    if (ec.getEdiKind().contains("출근")){

                        if (ad.getAttEndTime().before(sch.getWorkPattern().getWokEndTime())){

                            log.info("출근시간 정정 , 퇴근찍은 시간이 일정시간 보다 늦은 경우 조퇴");

                            ad.setAttStatus("조퇴");

                        }else {

                            log.info("출근시간 정정 , 퇴근찍은 시간이 일정시간이랑 같을 때 출근 ");

                            ad.setAttStatus("출근");
                        }

                        ad.setAttStartTime(Time.valueOf(ec.getEdiTime()));

                        log.info("set start time ad : " + ad );
                    } else if (ec.getEdiKind().contains("퇴근")) {

                        if (ad.getAttStartTime().after(sch.getWorkPattern().getWokStartTime())){

                            log.info("퇴근시간 정정 , 출근찍은 시간이 일정시간 보다 늦은 경우 지각");

                            ad.setAttStatus("지각");

                        }else {

                            log.info("퇴근시간 정정 , 출근찍은 시간이 일정시간이랑 같을 때 출근 ");

                            ad.setAttStatus("출근");
                        }

                        ad.setAttEndTime(Time.valueOf(ec.getEdiTime()));

                        log.info("set end Time ad : " + ad);

                    } else if (ec.getEdiKind().contains("결근")) {

                        if (ec.getEdiKind().contains("지각")){

                            log.info("지각한 시간을 받아서 찍어준다. 지각인 경우 종료타임만 정시에 찍어줌 ");

                            ad.setAttStartTime(Time.valueOf(ec.getEdiTime()));
                            ad.setAttEndTime(sch.getWorkPattern().getWokEndTime());
                            ad.setAttStatus("지각");

                        } else if (ec.getEdiKind().contains("조퇴")) {
                            log.info("조퇴한 시간을 받아서 찍어준다. 조퇴인 경우 시작시간만 정시에 찍어준다.");

                            ad.setAttStartTime(sch.getWorkPattern().getWokStartTime());
                            ad.setAttEndTime(Time.valueOf(ec.getEdiTime()));
                            ad.setAttStatus("조퇴");

                        } else if (ec.getEdiKind().contains("근무")) {
                            log.info("모두 정시로 찍어준다.");

                            ad.setAttStartTime(sch.getWorkPattern().getWokStartTime());
                            ad.setAttEndTime(sch.getWorkPattern().getWokEndTime());
                            ad.setAttStatus("출근");
                        }
                    }
                    log.info("update ad : " + ad );

                    attendanceRepository.save(ad);
            }




            result = 1;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        return (result > 0 ) ? "성공!"  : "실패" ;
    }


    // 스케줄 정정 결재 상신
    @Transactional
    public String submitSchedule(EditSchedule2DTO schedule, MultipartFile approvalFile) {

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

            log.info("schedule  : " + schedule);


            ac.setApprovalMember(schedule.getCMember());
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

    // 서류 요청 상신
    @Transactional
    public String submitReqDocumnet(ApprovalReqDocument2DTO reqDocument, MultipartFile approvalFile) {

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


            ac.setApprovalMember(reqDocument.getCMember());
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

    //전결자 지정
    @Transactional
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
    @Transactional
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

    public List<ApprovalDTO> selectApproval(String payCode) {

        List<Approval> approvalList = approvalRepository.findByPayCode(payCode);


        log.info("approval : " + approvalList);

        return approvalList.stream().map(approval -> modelMapper.map(approval, ApprovalDTO.class)).collect(Collectors.toList());
    }

    public List<ApprovalCompleteDTO> selectApprovalComplete(String payCode) {

        List<ApprovalComplete> approvalList = approvalCompleteRepository.findByApprovalPayCode(payCode);

        return approvalList.stream().map(approvalComplete -> modelMapper.map(approvalComplete, ApprovalCompleteDTO.class)).collect(Collectors.toList());
    }

    public List<ApprovalAttachmentDTO> selectApprovalAttachment(String payCode) {

        List<ApprovalAttachment> approvalAttachmentList = approvalAttachmentRepository.findByApprovalPayCode(payCode);

        return approvalAttachmentList.stream().map(approvalAttachment -> modelMapper.map(approvalAttachment, ApprovalAttachmentDTO.class)).collect(Collectors.toList());
    }


    public Object selectApprovalType(String payCode) {

        List<Approval> approval = approvalRepository.findByPayCode(payCode);

        log.info("approval : " + approval.get(0));

        if (approval.get(0).getPayKind().contains("연차")){
            log.info("연차");
            return modelMapper.map(approvalAnnualRepository.findByApprovalPayCode(payCode), ApprovalAnnualDTO.class);
        } else if (approval.get(0).getPayKind().contains("스케줄")) {
            log.info("스케줄");
            return modelMapper.map(editScheduleRepository.findByApprovalPayCode(payCode), EditScheduleDTO.class);
        } else if (approval.get(0).getPayKind().contains("출퇴근")) {
            log.info("출퇴근");
            return modelMapper.map(editCommuteRepository.findByApprovalPayCode(payCode), EditCommuteDTO.class);
        } else if (approval.get(0).getPayKind().contains("서류")) {
            log.info("서류");
            return modelMapper.map(approvalReqDocumentRepository.findByApprovalPayCode(payCode), ApprovalReqDocumentDTO.class);
        } else if (approval.get(0).getPayKind().contains("퇴직")){
            log.info("퇴직");
            return modelMapper.map(approvalRetiredRepository.findByApprovalPayCode(payCode), ApprovalRetiredDTO.class);
        }
        return "잘못된 요청입니다.";
    }

    public List<ApprovalMember2DTO> searchDate(ApprovalDateDTO date) {

        String startDate = date.getProStartDate();
        String endDate = date.getProEndDate();

        List<ApprovalAnnual> annuals = approvalAnnualRepository.findOverlappingVacations(startDate, endDate);

        List<String> payCode = new ArrayList<>();

        for (int i = 0; i < annuals.size(); i++) {
            payCode.add(annuals.get(i).getApproval().getPayCode());
        }

        List<Long> memCode = new ArrayList<>();

        for (int i = 0; i < payCode.size(); i++) {

            memCode.add(approvalRepository.findByPayCode(payCode.get(i)).get(0).getApprovalMember().getMemCode());

        }
        memCode.add(date.getMemCode());

        ApprovalMember member = approvalMemberRepository.findByMemCode(date.getMemCode());

        List<ApprovalMember2> memberList = approvalMember2Repository.findByDepartmentDepCode(member.getDepartment().getDepCode());

        List<ApprovalMember2> filterMember = memberList.stream().filter((member1) -> !memCode.contains(member1.getMemCode())).collect(Collectors.toList());

        log.info("filter member : " + filterMember);

        log.info("department : " + filterMember.get(0).getDepartment());

        return filterMember.stream().map(a -> modelMapper.map(a, ApprovalMember2DTO.class)).collect(Collectors.toList());
    }
}
