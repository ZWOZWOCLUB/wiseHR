package com.wisehr.wisehr.main.controller;

import com.wisehr.wisehr.common.ResponseDTO;
import com.wisehr.wisehr.main.service.MainService;
import com.wisehr.wisehr.schedule.dto.ScheduleSearchValueDTO;
import com.wisehr.wisehr.schedule.service.ScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("main")
@Slf4j
public class MainController {

    private final MainService mainService;

    private final ScheduleService scheduleService;

    public MainController(MainService mainService, ScheduleService scheduleService) {
        this.mainService = mainService;
        this.scheduleService = scheduleService;
    }

    /**
     * 결재 승인 내역 조회(내역 중 승인, 대기, 반려의 개수)
     * @return
     */
    @GetMapping("/approvalCount/{memCode}")
    public ResponseEntity<ResponseDTO> selectApprovalCount(@PathVariable Long memCode){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", mainService.selectApprovalCount(memCode)));
    }

//    @GetMapping("/todaySchedule/{memcode}")
//    public ResponseEntity<ResponseDTO> todaySchedule(@PathVariable Long memcode){
//        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", mainService.todaySchedule(memcode)));
//    }


    @GetMapping("/searchMonth")
    public ResponseEntity<ResponseDTO> searchMonth(@RequestBody ScheduleSearchValueDTO value) {
        if(value.getYearMonth() == null){
            Date date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
            System.out.println("simpleDateFormat = " + simpleDateFormat);
            String now = simpleDateFormat.format(date);
            value.setYearMonth(now);
            return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", mainService.searchMonth(value)));

        }   else {
            return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", scheduleService.searchMonth(value)));
        }
    }
}
