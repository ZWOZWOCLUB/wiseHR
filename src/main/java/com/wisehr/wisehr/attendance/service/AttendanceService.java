package com.wisehr.wisehr.attendance.service;

import com.wisehr.wisehr.approval.entity.*;
import com.wisehr.wisehr.approval.repository.*;
import com.wisehr.wisehr.attendance.dto.AttendanceDTO;
import com.wisehr.wisehr.attendance.dto.AttendanceDateDTO;
import com.wisehr.wisehr.attendance.dto.AttendanceScheduleDTO;
import com.wisehr.wisehr.attendance.dto.TodayInfoDTO;
import com.wisehr.wisehr.attendance.entity.Attendance;
import com.wisehr.wisehr.attendance.repository.AttendanceRepository;
import com.wisehr.wisehr.schedule.dto.ScheduleAllSelectDTO;
import com.wisehr.wisehr.schedule.dto.ScheduleSearchValueDTO;
import com.wisehr.wisehr.schedule.entity.ScheduleAllSelect;
import com.wisehr.wisehr.schedule.entity.ScheduleAllowance;
import com.wisehr.wisehr.schedule.repository.ScheduleAllSelectRepository;
import com.wisehr.wisehr.schedule.repository.ScheduleAllowanceRepository;
import com.wisehr.wisehr.util.ApprovalUtils;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final ApprovalScheduleRepository attendanceScheduleRepository;
    private final ScheduleAllowanceRepository attendanceScheduleAllowanceRepository;
    private final ApprovalWorkPatternRepository attendanceWorkPatternRepository;
    private final ApprovalPatternDayRepository attendancePatternDayRepository;
    private final ApprovalCompleteRepository approvalCompleteRepository;
    private final ReferencerRepository referencerRepository;
    private final ScheduleAllSelectRepository allSelectRepository;
    private final ApproverProxyRepository proxyRepository;
    private final ModelMapper modelMapper;
    private final ApprovalUtils utils;

    public AttendanceService(AttendanceRepository attendanceRepository, ApprovalScheduleRepository attendanceScheduleRepository, ScheduleAllowanceRepository attendanceScheduleAllowanceRepository, ApprovalWorkPatternRepository attendanceWorkPatternRepository, ApprovalPatternDayRepository attendancePatternDayRepository, ApprovalCompleteRepository approvalCompleteRepository, ReferencerRepository referencerRepository, ScheduleAllSelectRepository allSelectRepository, ApproverProxyRepository proxyRepository, ModelMapper modelMapper, ApprovalUtils utils) {
        this.attendanceRepository = attendanceRepository;
        this.attendanceScheduleRepository = attendanceScheduleRepository;
        this.attendanceScheduleAllowanceRepository = attendanceScheduleAllowanceRepository;
        this.attendanceWorkPatternRepository = attendanceWorkPatternRepository;
        this.attendancePatternDayRepository = attendancePatternDayRepository;
        this.approvalCompleteRepository = approvalCompleteRepository;
        this.referencerRepository = referencerRepository;
        this.allSelectRepository = allSelectRepository;
        this.proxyRepository = proxyRepository;
        this.modelMapper = modelMapper;
        this.utils = utils;
    }


    // 출근찍기
    @Transactional
    public String startWork(AttendanceDTO att) {

        try {

            List<ScheduleAllowance> scheduleList = attendanceScheduleAllowanceRepository.findByAllowanceID_MemCode((att.getAttendanceMember().getMemCode()).intValue());

            log.info("getSchCode : " + scheduleList);

            List<ApprovalSchedule> getWokCode = new ArrayList<>();

            String needSchCode = null;

            Map<String,List<LocalDate>> schWorkDayMap = new HashMap<>();

            LocalDate workDate = att.getAttWorkDate().toLocalDate();




            for (int i = 0; i < scheduleList.size(); i++) {
                getWokCode.add(attendanceScheduleRepository.findBySchCode(scheduleList.get(i).getAllowanceID().getSchCode()));
            }

            log.info("getWokCode : " + getWokCode);
            log.info("length : " + getWokCode.size());




            for (int i = 0; i < getWokCode.size(); i++) {
                List<ApprovalPatternDay> getPatternDay = new ArrayList<>();
                getPatternDay.addAll(attendancePatternDayRepository.findByApprovalPatternDayPK_WokCode(getWokCode.get(i).getWorkPattern().getWokCode()));
                log.info("getPatternDay : " + getPatternDay);
                log.info("getPatternDay length : " + getPatternDay.size());

                int[] day = new int[getPatternDay.size()];

                for (int j = 0; j < getPatternDay.size(); j++) {
                    day[j] = (getPatternDay.get(j).getApprovalPatternDayPK().getDayCode()).intValue();
                }

                log.info("day now : " + day);
                schWorkDayMap.put(getWokCode.get(i).getSchCode(), utils.findDays(getWokCode.get(i).getSchStartDate(),getWokCode.get(i).getSchEndDate(),day));

                for (Map.Entry<String, List<LocalDate>> entry : schWorkDayMap.entrySet()){
                    log.info("엔트리 값 : " + entry.getValue());
                    log.info("날짜 값 : " + workDate);
                    if (entry.getValue().contains(workDate)){
                        log.info("entry.getValue : " + entry.getValue());
                        log.info("여기 들어오냐?");
                        needSchCode = entry.getKey();
                    }
                }

            }

            ApprovalSchedule schSet = attendanceScheduleRepository.findBySchCode(needSchCode);
            log.info("wokCode : " + schSet);


            LocalTime startTime = attendanceWorkPatternRepository.findById(schSet.getWorkPattern().getWokCode()).get().getWokStartTime().toLocalTime();

            log.info("startTime : " + startTime);
            log.info("찍은 시간 : " + att.getAttStartTime() );


            if (needSchCode != null){
                if (att.getAttStartTime().toLocalTime().compareTo(startTime) > 0){
                    // 지금 시간이 찍은 시간보다 이후인 경우
                    att.setAttStatus("지각");
                    log.info("지각");
                } else if ((att.getAttStartTime().toLocalTime().compareTo(startTime) == 0) || att.getAttStartTime().toLocalTime().compareTo(startTime) < 0) {
                    // 지금 시간이 찍은 시간보다 이전인 경우
                    // 퇴근 때 찍으면 출근으로 변경
                    att.setAttStatus("조퇴");
                    log.info("조퇴");
                }

            }
            att.setAttStartTime(att.getAttStartTime());
            att.setAttendanceSchedule(modelMapper.map(schSet, AttendanceScheduleDTO.class));


            log.info("att : " + att );
            att.setAttValue(1);
            attendanceRepository.save(modelMapper.map(att, Attendance.class));

        } catch (Exception e) {
            e.printStackTrace();
            return "일정이 없습니다. 출근이 기록되지 않습니다.";
        }

        return "출근을 환영합니다. \n 출근 시간은 " + att.getAttStartTime().toLocalTime() + " 입니다.";
    }

    // 퇴근찍기
    @Transactional
    public String endWork(AttendanceDTO att) {

        try {
            Attendance endSch1  = attendanceRepository.findFirstByAttendanceMemberMemCodeOrderByAttWorkDateDesc(att.getAttendanceMember().getMemCode());

            ApprovalSchedule getSch = attendanceScheduleRepository.findBySchCode(endSch1.getAttendanceSchedule().getSchCode());

            log.info("endSch1 : " + endSch1);

            Attendance endSch = attendanceRepository.findFirstByAttendanceMemberMemCodeAndAttendanceSchedule_SchCodeOrderByAttWorkDateDesc(att.getAttendanceMember().getMemCode(),endSch1.getAttendanceSchedule().getSchCode());

            log.info("endSch : " + endSch);

            LocalTime endTime  = attendanceWorkPatternRepository.findById(getSch.getWorkPattern().getWokCode()).get().getWokEndTime().toLocalTime();

            log.info("endTime : " + endTime);

            endSch.setAttEndTime(att.getAttEndTime());

            att = modelMapper.map(endSch, AttendanceDTO.class);

            log.info("att : " + att);

            if (att.getAttEndTime().toLocalTime().compareTo(endTime) > 0){
                // 퇴근 시간 이후로 퇴근 찍기
                if (att.getAttStatus().equals("조퇴")){
                    att.setAttStatus("출근");
                    log.info("퇴근 출근");
                }
            } else if (att.getAttEndTime().toLocalTime().compareTo(endTime) < 0) {
                // 퇴근 시간 이전에 퇴근 찍기
                att.setAttStatus("조퇴");
                log.info("퇴근 조퇴");
            }

            att.setAttValue(2);

            attendanceRepository.save(modelMapper.map(att, Attendance.class));
            log.info("코딩 킹 갓 제너럴 ");



        }catch (Exception e){
            e.printStackTrace();
            return "오류가 발생했습니다. ";
        }

        return "퇴근이 완료되었습니다. \n 퇴근시간은 " + att.getAttEndTime() + " 입니다. \n 고생하셨습니다.";
    }

    public List<AttendanceDateDTO> searchDate(String memCode , String searchDate) {
        log.info("날짜 찾기 서비스 시작 ");

        List<ScheduleAllowance> scheduleList = attendanceScheduleAllowanceRepository.findByAllowanceID_MemCode(Integer.parseInt(memCode));

        log.info("getSchCode : " + scheduleList);

        List<ApprovalSchedule> getWokCode = new ArrayList<>();

        String needSchCode = null;

        Map<String,List<LocalDate>> schWorkDayMap = new HashMap<>();

        LocalDate workDate = LocalDate.parse(searchDate);




        for (int i = 0; i < scheduleList.size(); i++) {
            getWokCode.add(attendanceScheduleRepository.findBySchCode(scheduleList.get(i).getAllowanceID().getSchCode()));
        }

        log.info("getWokCode : " + getWokCode);
        log.info("length : " + getWokCode.size());




        for (int i = 0; i < getWokCode.size(); i++) {
            List<ApprovalPatternDay> getPatternDay = new ArrayList<>();
            getPatternDay.addAll(attendancePatternDayRepository.findByApprovalPatternDayPK_WokCode(getWokCode.get(i).getWorkPattern().getWokCode()));
            log.info("getPatternDay : " + getPatternDay);
            log.info("getPatternDay length : " + getPatternDay.size());

            int[] day = new int[getPatternDay.size()];

            for (int j = 0; j < getPatternDay.size(); j++) {
                day[j] = (getPatternDay.get(j).getApprovalPatternDayPK().getDayCode()).intValue();
            }

            log.info("day now : " + day);
            schWorkDayMap.put(getWokCode.get(i).getSchCode(), utils.findDays(getWokCode.get(i).getSchStartDate(),getWokCode.get(i).getSchEndDate(),day));

            for (Map.Entry<String, List<LocalDate>> entry : schWorkDayMap.entrySet()){
                log.info("엔트리 값 : " + entry.getValue());
                log.info("날짜 값 : " + workDate);
                if (entry.getValue().contains(workDate)){
                    log.info("entry.getValue : " + entry.getValue());
                    log.info("여기 들어오냐?");
                    needSchCode = entry.getKey();
                }
            }

        }


        if (needSchCode == null ){
            log.info("가라 ");
            return null;
        }

        ApprovalSchedule schSet = attendanceScheduleRepository.findBySchCode(needSchCode);
        log.info("wokCode : " + schSet);

        List<ScheduleAllowance> memberList = attendanceScheduleAllowanceRepository.findByAllowanceID_SchCode(needSchCode);

        log.info("memberList : " + memberList);


        List<AttendanceDateDTO> result = new ArrayList<>();
        log.info("??? : " + memberList.size());

        for (int i = 0; i < memberList.size(); i++) {
            AttendanceDateDTO addResult = new AttendanceDateDTO();
            addResult.setPosName(memberList.get(i).getMemberList().getPosCode().getPosName());
            addResult.setMemName(memberList.get(i).getMemberList().getMemName());
            addResult.setStartTime(String.valueOf(schSet.getWorkPattern().getWokStartTime()));
            addResult.setEndTime(String.valueOf(schSet.getWorkPattern().getWokEndTime()));
            addResult.setDepName(memberList.get(i).getMemberList().getDepCode().getDepName());
            result.add(addResult);
            log.info("result " + i + " " + result);
        }

        return result;
    }

    public TodayInfoDTO todayInfo(String searchDate, String memCode) {

        List<ApprovalComplete> complete = approvalCompleteRepository.findByApprovalApprovalMemberMemCodeAndAppState(Long.parseLong(memCode),"승인");

        log.info("complete : " + complete);
        log.info("complete count : " + complete.size());

        List<ApprovalComplete> nagative = approvalCompleteRepository.findByApprovalApprovalMemberMemCodeAndAppState(Long.parseLong(memCode),"반려");

        log.info(" nagative : " + nagative);
        log.info(" nagative count : " + nagative.size());

        List<ApprovalComplete> stay = approvalCompleteRepository.findByApprovalApprovalMemberMemCodeAndAppState(Long.parseLong(memCode),"대기");

        log.info(" stay : " + stay);
        log.info(" stay count : " + stay.size());

        List<Referencer> reference = referencerRepository.findByReferencerPK_MemCode(Long.parseLong(memCode));

        log.info("reference : " + reference);
        log.info("reference count : " + reference.size());

        TodayInfoDTO today = new TodayInfoDTO();

        today.setCompleteNumber(complete.size());
        today.setNagativeNumber(nagative.size());
        today.setStayNumber(stay.size());
        today.setReferencerNumber(reference.size());



        List<ScheduleAllowance> scheduleList = attendanceScheduleAllowanceRepository.findByAllowanceID_MemCode(Integer.parseInt(memCode));

        log.info("getSchCode : " + scheduleList);

        List<ApprovalSchedule> getWokCode = new ArrayList<>();

        String needSchCode = null;

        Map<String,List<LocalDate>> schWorkDayMap = new HashMap<>();

        LocalDate workDate = LocalDate.parse(searchDate);




        for (int i = 0; i < scheduleList.size(); i++) {
            getWokCode.add(attendanceScheduleRepository.findBySchCode(scheduleList.get(i).getAllowanceID().getSchCode()));
        }

        log.info("getWokCode : " + getWokCode);
        log.info("length : " + getWokCode.size());




        for (int i = 0; i < getWokCode.size(); i++) {
            List<ApprovalPatternDay> getPatternDay = new ArrayList<>();
            getPatternDay.addAll(attendancePatternDayRepository.findByApprovalPatternDayPK_WokCode(getWokCode.get(i).getWorkPattern().getWokCode()));
            log.info("getPatternDay : " + getPatternDay);
            log.info("getPatternDay length : " + getPatternDay.size());

            int[] day = new int[getPatternDay.size()];

            for (int j = 0; j < getPatternDay.size(); j++) {
                day[j] = (getPatternDay.get(j).getApprovalPatternDayPK().getDayCode()).intValue();
            }

            log.info("day now : " + day);
            schWorkDayMap.put(getWokCode.get(i).getSchCode(), utils.findDays(getWokCode.get(i).getSchStartDate(),getWokCode.get(i).getSchEndDate(),day));

            for (Map.Entry<String, List<LocalDate>> entry : schWorkDayMap.entrySet()){
                log.info("엔트리 값 : " + entry.getValue());
                log.info("날짜 값 : " + workDate);
                if (entry.getValue().contains(workDate)){
                    log.info("entry.getValue : " + entry.getValue());
                    log.info("여기 들어오냐?");
                    needSchCode = entry.getKey();
                }
            }

        }


        if (needSchCode == null ){
            log.info("가라 ");
            return today;
        }

        ApprovalSchedule schSet = attendanceScheduleRepository.findBySchCode(needSchCode);
        log.info("wokCode : " + schSet);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Attendance value = null;
        try {
            value = attendanceRepository.findByAttendanceMemberMemCodeAndAttWorkDateAndAttendanceScheduleSchCode(Long.parseLong(memCode), dateFormat.parse(searchDate), schSet.getSchCode());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        log.info("value : " + value);

        today.setStartTime(String.valueOf(schSet.getWorkPattern().getWokStartTime()));
        today.setEndTime(String.valueOf(schSet.getWorkPattern().getWokEndTime()));

        if (value != null){
            if (value.getAttValue() != null){
                today.setAttValue(value.getAttValue());
            }
            if (value.getAttStartTime() != null){
                today.setAttStartTime(String.valueOf(value.getAttStartTime()));
            }
            if (value.getAttEndTime() != null){
                today.setAttEndTime(String.valueOf(value.getAttEndTime()));
            }
        }

        log.info("머임?");
        log.info("today : " + today);

        return today;
    }

    public List<ScheduleAllSelectDTO> searchMonth(ScheduleSearchValueDTO value) {


        List<ScheduleAllSelect> allSelect = allSelectRepository.findByYearMonth(value.getMemCode());

        log.info("allSelect : " + allSelect);

        List<ScheduleAllSelectDTO> selectDTOList = allSelect.stream()
                .map(list -> modelMapper.map(list, ScheduleAllSelectDTO.class))
                .collect(Collectors.toList());

        log.info("selectDTOLIST : " + selectDTOList);

        return selectDTOList;
    }

    public boolean getProxy(String memCode, String date) {


        boolean result = false;

        List<ApproverProxy> result1 =  proxyRepository.findByProMemberMemCode(Long.parseLong(memCode));

        log.info("result1  : " + result1);

        if (result1.size() > 0 ){
            log.info("size : " + result1.size());
            result = true;
        }



        return result;
    }
}
