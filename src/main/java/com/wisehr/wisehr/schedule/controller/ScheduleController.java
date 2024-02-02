package com.wisehr.wisehr.schedule.controller;

import com.wisehr.wisehr.common.ResponseDTO;
import com.wisehr.wisehr.schedule.dto.*;
import com.wisehr.wisehr.schedule.service.ScheduleService;
import com.wisehr.wisehr.setting.dto.SettingCareerDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }




/**
 * 1. 첫페이지
 *  일정 조회(첫페이지에 전체 일정이 다 뜨게 이번달꺼만)
 */

/**
 * 1. 부서 조회(조직도 형태)
 */

/**
 * 1. 선택한 다른 사람 또는 부서의 일정, 년 - 월 조회
 * /

/**
 *
 * 1. 이전 스케줄 조회 (년-월로 조회 schedule테이블이 아닌 attendance 테이블로 가져옴)
 */
@GetMapping("/searchPrev/{yearMonth}")
    public ResponseEntity<ResponseDTO> searchPrev(@PathVariable String yearMonth) {
    return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", scheduleService.searchPrev(yearMonth)));
}

    /**
     * 1. 다음달 일정 조회(날짜에 맞는 패턴이 있으면 가져오고 없으면 미출력)
     */

    /**
     * 2. 해당 날짜 누르면 뜨는 창
     * 그날의 근무그룹 별로 지정된 사람, 미등록된 사람 뜸
     */

    /**
     * 2. 해당 날짜 누르면 뜨는 참
     * 부서별로 사람 검색(각 부서에 몇명이 있는지도 count)
     */


    /**
     * 3. 새 근무 편성 관련
     * 근무패턴 테이블 insert
     */
    @PostMapping("/workPattern")
    public ResponseEntity<ResponseDTO> insertWorkPattern(@RequestBody ScheduleWorkPatternDTO patternDTO){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "근무패턴 등록 성공", scheduleService.insertWorkPattern(patternDTO)));
    }
    /**
     * 3.새 근무 편성 관련
     * 근무 패턴 수정
     */
    @PutMapping(value = "/workPattern")
    public ResponseEntity<ResponseDTO> updateWorkPattern(@RequestBody ScheduleWorkPatternDTO patternDTO){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "근무패턴 수정 성공", scheduleService.updateWorkPattern(patternDTO)));
    }
    /**
     * 4. 새 근무 그룹 관련
     * 일정(schedule) 테이블 insert -> 근무 패턴 요일 테이블 insert
     */
    @PostMapping("/schedule")
    public ResponseEntity<ResponseDTO> insertSchedule(@RequestBody ScheduleInsertDTO insertDTO){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "근무그룹 등록 성공", scheduleService.insertSchedule(insertDTO)));
    }

    /**
     * 4. 새 근무 그룹 관련
     * 일정 테이블, 근무 패턴 요일 테이블 수정
     */
    @PutMapping(value = "/schedule")
    public ResponseEntity<ResponseDTO> updateSchedule(@RequestBody ScheduleInsertDTO insertDTO){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "근무그룹 수정 성공", scheduleService.updateSchedule(insertDTO)));
    }


    /**
     * 5. 새 근무 그룹 관련
     * 근무 그룹에 사람 넣으면
     * schedule_allowance에 insert
     */
    @PostMapping("/allowance")
    public ResponseEntity<ResponseDTO> insertScheduleAllowance(@RequestBody ScheduleAllowanceDTO allowanceDTO) {
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "근무 그룹에 사람 등록 성공", scheduleService.insertScheduleAllowance(allowanceDTO)));
    }
    /**
     * 5. 새 근무 그룹 관련
     * 근무 그룹 인원 수정
     * schedule_allowance 수정
     */
    @PutMapping(value = "/allowance")
    public ResponseEntity<ResponseDTO> updateScheduleAllowance(@RequestBody ScheduleAllowanceDTO allowanceDTO){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "근무 그룹에 사람 수정 성공", scheduleService.updateScheduleAllowance(allowanceDTO)));
    }

    /**
     * 6. 근무외 일정추가
     * etc_pattern 테이블에 insert
     */
    @PostMapping("/etcPattern")
    public ResponseEntity<ResponseDTO> insertEtcPattern(@RequestBody ScheduleEtcPatternDTO etcPatternDTO){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "근무외 일정 등록 성공", scheduleService.insertEtcPattern(etcPatternDTO)));
    }

    /**
     * 6. 근무외 일정추가
     * etc_pattern 테이블 수정
     */
    @PutMapping(value = "/etcPattern")
    public ResponseEntity<ResponseDTO> updateEtcPattern(@RequestBody ScheduleEtcPatternDTO etcPatternDTO){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "근무외 일정 수정 성공", scheduleService.updateEtcPattern(etcPatternDTO)));
    }

}

