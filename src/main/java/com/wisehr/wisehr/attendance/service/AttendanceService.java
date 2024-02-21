package com.wisehr.wisehr.attendance.service;

import com.wisehr.wisehr.approval.entity.ApprovalComplete;
import com.wisehr.wisehr.approval.entity.ApprovalPatternDay;
import com.wisehr.wisehr.approval.entity.ApprovalSchedule;
import com.wisehr.wisehr.approval.entity.Referencer;
import com.wisehr.wisehr.approval.repository.*;
import com.wisehr.wisehr.attendance.dto.AttendanceDTO;
import com.wisehr.wisehr.attendance.dto.AttendanceDateDTO;
import com.wisehr.wisehr.attendance.dto.AttendanceScheduleDTO;
import com.wisehr.wisehr.attendance.dto.TodayInfoDTO;
import com.wisehr.wisehr.attendance.entity.Attendance;
import com.wisehr.wisehr.attendance.repository.AttendanceRepository;
import com.wisehr.wisehr.schedule.entity.ScheduleAllowance;
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
    private final ModelMapper modelMapper;
    private final ApprovalUtils utils;

    public AttendanceService(AttendanceRepository attendanceRepository, ApprovalScheduleRepository attendanceScheduleRepository, ScheduleAllowanceRepository attendanceScheduleAllowanceRepository, ApprovalWorkPatternRepository attendanceWorkPatternRepository, ApprovalPatternDayRepository attendancePatternDayRepository, ApprovalCompleteRepository approvalCompleteRepository, ReferencerRepository referencerRepository, ModelMapper modelMapper, ApprovalUtils utils) {
        this.attendanceRepository = attendanceRepository;
        this.attendanceScheduleRepository = attendanceScheduleRepository;
        this.attendanceScheduleAllowanceRepository = attendanceScheduleAllowanceRepository;
        this.attendanceWorkPatternRepository = attendanceWorkPatternRepository;
        this.attendancePatternDayRepository = attendancePatternDayRepository;
        this.approvalCompleteRepository = approvalCompleteRepository;
        this.referencerRepository = referencerRepository;
        this.modelMapper = modelMapper;
        this.utils = utils;
    }


    // 출근찍기
    @Transactional
    public String startWork(AttendanceDTO att) {

        try {
            List<ScheduleAllowance> getSchCode = attendanceScheduleAllowanceRepository.findByAllowanceID_MemCode((att.getAttendanceMember().getMemCode()).intValue());  // 가지고 있는 스케줄 코드를 가져옴 2개 이상일 수 있어서 List

            log.info("getSchCode : " + getSchCode);

            List<ApprovalSchedule> getWokCode = new ArrayList<>();
            List<ApprovalPatternDay> getPatternDay = new ArrayList<>();

            for (int i = 0; i < getSchCode.size(); i++) {
                getWokCode.add(attendanceScheduleRepository.findBySchCode(getSchCode.get(i).getAllowanceID().getSchCode()));   // 찾아온 schCode를 통해서 wokCode를 찾기
                getPatternDay.addAll(attendancePatternDayRepository.findByApprovalPatternDayPK_WokCode(getWokCode.get(i).getWorkPattern().getWokCode()));   // wokCode를 통해서 일하는 요일을 가져오기
            }

            log.info("getWokCode : " + getWokCode);
            log.info("getPatternDay : " + getPatternDay);

            int[] day = new int[getPatternDay.size()];

            for (int i = 0; i < getPatternDay.size(); i++) {
                day[i] = (getPatternDay.get(i).getApprovalPatternDayPK().getDayCode()).intValue();
            }

            int[] days = Arrays.stream(day).distinct().toArray();   // 배열의 값을 유니크한 값만 남기도록 (어차피 날짜가 중복되게 짜지 않을거니까 )

            Map<String,List<LocalDate>> schWorkDayMap = new HashMap<>();

            for (int i = 0; i < getWokCode.size(); i++) {
                schWorkDayMap.put(getWokCode.get(i).getSchCode(), utils.findDays(getWokCode.get(i).getSchStartDate(),getWokCode.get(i).getSchEndDate(),days)); // 날짜 구하는 매서드에 값 넣어서 일하는 날짜 뽑아내기
            }

            log.info("days : " + days);
            log.info("schWorkDayMap : " + schWorkDayMap);
            log.info("sch10 : " +schWorkDayMap.get("sch10"));

            LocalDate workDate = att.getAttWorkDate().toLocalDate();
            String needSchCode = "";


            for (Map.Entry<String, List<LocalDate>> entry : schWorkDayMap.entrySet()){
                log.info("엔트리 값 : " + entry.getValue());
                log.info("날짜 값 : " + workDate);
                if (entry.getValue().contains(workDate)){
                    log.info("entry.getValue : " + entry.getValue());
                    log.info("여기 들어오냐?");
                    needSchCode = entry.getKey();
                }
            }
            log.info("needSchCode  : " + needSchCode);

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
        List<ApprovalPatternDay> getPatternDay = new ArrayList<>();

        for (int i = 0; i < scheduleList.size(); i++) {
            getWokCode.add(attendanceScheduleRepository.findBySchCode(scheduleList.get(i).getAllowanceID().getSchCode()));
            getPatternDay.addAll(attendancePatternDayRepository.findByApprovalPatternDayPK_WokCode(getWokCode.get(i).getWorkPattern().getWokCode()));
        }

        log.info("getWokCode : " + getWokCode);
        log.info("getPatternDay : " + getPatternDay);

        int[] day = new int[getPatternDay.size()];

        for (int i = 0; i < getPatternDay.size(); i++) {
            day[i] = (getPatternDay.get(i).getApprovalPatternDayPK().getDayCode()).intValue();
        }

        int[] days = Arrays.stream(day).distinct().toArray();

        Map<String,List<LocalDate>> schWorkDayMap = new HashMap<>();

        for (int i = 0; i < getWokCode.size(); i++) {
            schWorkDayMap.put(getWokCode.get(i).getSchCode(), utils.findDays(getWokCode.get(i).getSchStartDate(),getWokCode.get(i).getSchEndDate(),days));
        }

        log.info("days : " + days);
        log.info("schWorkDayMap : " + schWorkDayMap);

        LocalDate workDate = LocalDate.parse(searchDate);
        String needSchCode = null;

        log.info("workDate : " + workDate);


        for (Map.Entry<String, List<LocalDate>> entry : schWorkDayMap.entrySet()){
            log.info("엔트리 값 : " + entry.getValue());
            log.info("날짜 값 : " + workDate);
            if (entry.getValue().contains(workDate)){
                log.info("entry.getValue : " + entry.getValue());
                log.info("여기 들어오냐?");
                needSchCode = entry.getKey();
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

        List<ScheduleAllowance> scheduleList = attendanceScheduleAllowanceRepository.findByAllowanceID_MemCode(Integer.parseInt(memCode));

        log.info("getSchCode : " + scheduleList);

        List<ApprovalSchedule> getWokCode = new ArrayList<>();
        List<ApprovalPatternDay> getPatternDay = new ArrayList<>();

        for (int i = 0; i < scheduleList.size(); i++) {
            getWokCode.add(attendanceScheduleRepository.findBySchCode(scheduleList.get(i).getAllowanceID().getSchCode()));
            getPatternDay.addAll(attendancePatternDayRepository.findByApprovalPatternDayPK_WokCode(getWokCode.get(i).getWorkPattern().getWokCode()));
        }

        log.info("getWokCode : " + getWokCode);
        log.info("getPatternDay : " + getPatternDay);

        int[] day = new int[getPatternDay.size()];

        for (int i = 0; i < getPatternDay.size(); i++) {
            day[i] = (getPatternDay.get(i).getApprovalPatternDayPK().getDayCode()).intValue();
        }

        int[] days = Arrays.stream(day).distinct().toArray();

        Map<String,List<LocalDate>> schWorkDayMap = new HashMap<>();

        for (int i = 0; i < getWokCode.size(); i++) {
            schWorkDayMap.put(getWokCode.get(i).getSchCode(), utils.findDays(getWokCode.get(i).getSchStartDate(),getWokCode.get(i).getSchEndDate(),days));
        }

        log.info("days : " + days);
        log.info("schWorkDayMap : " + schWorkDayMap);

        LocalDate workDate = LocalDate.parse(searchDate);
        String needSchCode = null;

        log.info("workDate : " + workDate);

        TodayInfoDTO today = new TodayInfoDTO();

        today.setCompleteNumber(complete.size());
        today.setNagativeNumber(nagative.size());
        today.setStayNumber(stay.size());
        today.setReferencerNumber(reference.size());


        for (Map.Entry<String, List<LocalDate>> entry : schWorkDayMap.entrySet()){
            log.info("엔트리 값 : " + entry.getValue());
            log.info("날짜 값 : " + workDate);
            if (entry.getValue().contains(workDate)){
                log.info("entry.getValue : " + entry.getValue());
                log.info("여기 들어오냐?");
                needSchCode = entry.getKey();
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
}
