package com.wisehr.wisehr.attendance.service;

import com.wisehr.wisehr.approval.entity.ApprovalPatternDay;
import com.wisehr.wisehr.approval.entity.ApprovalSchedule;
import com.wisehr.wisehr.approval.repository.ApprovalPatternDayRepository;
import com.wisehr.wisehr.approval.repository.ApprovalScheduleRepository;
import com.wisehr.wisehr.approval.repository.ApprovalWorkPatternRepository;
import com.wisehr.wisehr.attendance.dto.AttendanceDTO;
import com.wisehr.wisehr.attendance.repository.AttendanceRepository;
import com.wisehr.wisehr.schedule.entity.Schedule;
import com.wisehr.wisehr.schedule.entity.ScheduleAllowance;
import com.wisehr.wisehr.schedule.repository.ScheduleAllowanceRepository;
import com.wisehr.wisehr.util.ApprovalUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@Slf4j
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final ApprovalScheduleRepository attendanceScheduleRepository;
    private final ScheduleAllowanceRepository attendanceScheduleAllowanceRepository;
    private final ApprovalWorkPatternRepository attendanceWorkPatternRepository;
    private final ApprovalPatternDayRepository attendancePatternDayRepository;
    private final ApprovalUtils utils;

    public AttendanceService(AttendanceRepository attendanceRepository, ApprovalScheduleRepository attendanceScheduleRepository, ScheduleAllowanceRepository attendanceScheduleAllowanceRepository, ApprovalWorkPatternRepository attendanceWorkPatternRepository, ApprovalPatternDayRepository attendancePatternDayRepository, ApprovalUtils utils) {
        this.attendanceRepository = attendanceRepository;
        this.attendanceScheduleRepository = attendanceScheduleRepository;
        this.attendanceScheduleAllowanceRepository = attendanceScheduleAllowanceRepository;
        this.attendanceWorkPatternRepository = attendanceWorkPatternRepository;
        this.attendancePatternDayRepository = attendancePatternDayRepository;
        this.utils = utils;
    }


    public String startWork(AttendanceDTO att) {

        List<ScheduleAllowance> getSchCode = attendanceScheduleAllowanceRepository.findByMemCode((att.getAttendanceMember().getMemCode()).intValue());  // 가지고 있는 스케줄 코드를 가져옴 2개 이상일 수 있어서 List

        log.info("getSchCode : " + getSchCode);

        List<ApprovalSchedule> getWokCode = new ArrayList<>();
        List<ApprovalPatternDay> getPatternDay = new ArrayList<>();

        for (int i = 0; i < getSchCode.size(); i++) {
            getWokCode.add(attendanceScheduleRepository.findBySchCode(getSchCode.get(i).getSchCode()));   // 찾아온 schCode를 통해서 wokCode를 찾기
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
        String needSchCode = "";

        for (Map.Entry<String, List<LocalDate>> entry : schWorkDayMap.entrySet()){
            if (entry.getValue().equals(att.getAttWorkDate())){
                needSchCode = entry.getKey();
                log.info("왜 안나오징 ?  " + needSchCode);
                break;
            }
        }

        log.info("needSchCode : " + needSchCode);

        return "메ㅐ롱";
    }
}
