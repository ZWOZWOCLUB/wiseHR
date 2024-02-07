package com.wisehr.wisehr.attendance.service;

import com.wisehr.wisehr.approval.entity.ApprovalPatternDay;
import com.wisehr.wisehr.approval.entity.ApprovalSchedule;
import com.wisehr.wisehr.approval.entity.ApprovalWorkPattern;
import com.wisehr.wisehr.approval.repository.ApprovalPatternDayRepository;
import com.wisehr.wisehr.approval.repository.ApprovalScheduleRepository;
import com.wisehr.wisehr.approval.repository.ApprovalWorkPatternRepository;
import com.wisehr.wisehr.attendance.dto.AttendanceDTO;
import com.wisehr.wisehr.attendance.dto.AttendanceScheduleDTO;
import com.wisehr.wisehr.attendance.entity.Attendance;
import com.wisehr.wisehr.attendance.repository.AttendanceRepository;
import com.wisehr.wisehr.schedule.entity.ScheduleAllowance;
import com.wisehr.wisehr.schedule.repository.ScheduleAllowanceRepository;
import com.wisehr.wisehr.util.ApprovalUtils;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
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
    private final ModelMapper modelMapper;
    private final ApprovalUtils utils;

    public AttendanceService(AttendanceRepository attendanceRepository, ApprovalScheduleRepository attendanceScheduleRepository, ScheduleAllowanceRepository attendanceScheduleAllowanceRepository, ApprovalWorkPatternRepository attendanceWorkPatternRepository, ApprovalPatternDayRepository attendancePatternDayRepository, ModelMapper modelMapper, ApprovalUtils utils) {
        this.attendanceRepository = attendanceRepository;
        this.attendanceScheduleRepository = attendanceScheduleRepository;
        this.attendanceScheduleAllowanceRepository = attendanceScheduleAllowanceRepository;
        this.attendanceWorkPatternRepository = attendanceWorkPatternRepository;
        this.attendancePatternDayRepository = attendancePatternDayRepository;
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

            int[] days = Arrays.stream(day).distinct().toArray();   // 배열의 값을 유니크한 값만 남기도록

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
                att.setAttStatus("출근");
                log.info("퇴근 출근");
            } else if (att.getAttEndTime().toLocalTime().compareTo(endTime) < 0) {
                // 퇴근 시간 이전에 퇴근 찍기
                att.setAttStatus("조퇴");
                log.info("퇴근 조퇴");
            }

            attendanceRepository.save(modelMapper.map(att, Attendance.class));
            log.info("코딩 킹 갓 제너럴 ");



        }catch (Exception e){
            e.printStackTrace();
            return "오류가 발생했습니다. ";
        }

        return "퇴근이 완료되었습니다. \n 퇴근시간은 " + att.getAttEndTime() + " 입니다. \n 고생하셨습니다.";
    }
}
