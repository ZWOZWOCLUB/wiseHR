package com.wisehr.wisehr.attendance.controller;

import com.wisehr.wisehr.attendance.dto.Attendance2DTO;
import com.wisehr.wisehr.attendance.dto.AttendanceDTO;
import com.wisehr.wisehr.attendance.service.AttendanceService;
import com.wisehr.wisehr.common.ResponseDTO;
import com.wisehr.wisehr.schedule.dto.ScheduleSearchValueDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;

@RestController
@RequestMapping("attendance")
@Slf4j
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @PostMapping("/on")
    public ResponseEntity<ResponseDTO> startWork(@RequestBody Attendance2DTO att1){

        log.info("att1 " + att1);


        AttendanceDTO att = new AttendanceDTO();

        att.setAttStartTime(Time.valueOf(att1.getAttStartTime()));
        log.info("Att1 " + att);
        att.setAttWorkDate(Date.valueOf(att1.getAttWorkDate()));
        log.info("Att2 " + att);
        att.setAttendanceMember(att1.getAttendanceMember());
        log.info("Att3 " + att);

        log.info("att : " + att);

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "출근 성공", attendanceService.startWork(att)));

    }

    @PutMapping("/off")
    public ResponseEntity<ResponseDTO> endWork(@RequestBody Attendance2DTO att1){

        AttendanceDTO att = new AttendanceDTO();
        log.info("att : " + att);
        att.setAttEndTime(Time.valueOf(att1.getAttEndTime()));
        att.setAttendanceMember(att1.getAttendanceMember());


        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "퇴근 성공", attendanceService.endWork(att)));
    }

    @GetMapping("/searchdate/{memCode}/date/{searchDate}")
    public ResponseEntity<ResponseDTO> searchDate(@PathVariable String memCode,
                                                  @PathVariable String searchDate){

        log.info("date123 : " + searchDate);
        log.info("memCode : " + memCode);

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", attendanceService.searchDate(memCode, searchDate)));
    }

    @GetMapping("{searchDate}/date/{memCode}")
    public ResponseEntity<ResponseDTO> todayInfo(@PathVariable String searchDate,
                                                 @PathVariable String memCode){

        log.info("오늘 날짜 : " + searchDate);
        log.info("사원 정보 : " + memCode);

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "당일 정보 조회 성공 " , attendanceService.todayInfo(searchDate,memCode)));
    }

    @PostMapping("{memCode}/schedule/{searchDate}")
    public ResponseEntity<ResponseDTO> searchMonth(@PathVariable String memCode,
                                                   @PathVariable String searchDate) {

        ScheduleSearchValueDTO value = new ScheduleSearchValueDTO();
        value.setMemCode(Integer.parseInt(memCode));
        value.setYearMonth(searchDate);

        log.info("memCode 123 : " + memCode);
        log.info("searchDate123 : " + searchDate);

        if(value.getYearMonth() == null){
            java.util.Date date = new java.util.Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
            System.out.println("simpleDateFormat = " + simpleDateFormat);
            String now = simpleDateFormat.format(date);
            value.setYearMonth(now);
            return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "개인 스케줄 조회 성공", attendanceService.searchMonth(value)));

        }   else {
            return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "개인 스케줄 조회 성공", attendanceService.searchMonth(value)));
        }
    }


    @GetMapping("{memCode}/proxy/{date}")
    public ResponseEntity<ResponseDTO> getProxy (@PathVariable String memCode,
                                                 @PathVariable String date) {

        log.info("memCode : " + memCode);
        log.info("date : " + date) ;

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "전결자야?", attendanceService.getProxy(memCode, date)));
    }


}
