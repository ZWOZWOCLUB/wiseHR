package com.wisehr.wisehr.attendance.controller;

import com.wisehr.wisehr.attendance.dto.AttendanceDTO;
import com.wisehr.wisehr.attendance.service.AttendanceService;
import com.wisehr.wisehr.common.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("attendance")
@Slf4j
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @PostMapping("/on")
    public ResponseEntity<ResponseDTO> startWork(@RequestBody AttendanceDTO att){

        log.info("att : " + att);

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "출근 성공", attendanceService.startWork(att)));

    }

    @PutMapping("/off")
    public ResponseEntity<ResponseDTO> endWork(@RequestBody AttendanceDTO att){

        log.info("att : " + att);

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "퇴근 성공", attendanceService.endWork(att)));
    }

    @GetMapping("/searchdate/{memCode}/date/{searchDate}")
    public ResponseEntity<ResponseDTO> searchDate(@PathVariable String memCode,
                                                  @PathVariable String searchDate){

        log.info("date123 : " + searchDate);
        log.info("memCode : " + memCode);

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", attendanceService.searchDate(memCode, searchDate)));
    }

}
