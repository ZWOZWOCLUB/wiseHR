package com.wisehr.wisehr.attendance.service;

import com.wisehr.wisehr.attendance.entity.MainSchedule;
import com.wisehr.wisehr.attendance.repository.MainAttendanceRepository;
import com.wisehr.wisehr.attendance.repository.MainScheduleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
@Slf4j
public class AttService {

    private final MainAttendanceRepository mainAttendanceRepository;
    private final MainScheduleRepository mainScheduleRepository;

    public AttService(MainAttendanceRepository mainAttendanceRepository, MainScheduleRepository mainScheduleRepository) {
        this.mainAttendanceRepository = mainAttendanceRepository;
        this.mainScheduleRepository = mainScheduleRepository;
    }

    public Object insertAtt() {


        //현재날짜와 시간 구하는 메소드
        LocalDate today = LocalDate.now();
        LocalTime currentTime = LocalTime.now();

        //현재 로그인한 사용자의 멤버코드
        Long memCode = 2L;



        return null;
    }
}
