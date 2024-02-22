package com.wisehr.wisehr.schedule.controller;

import com.wisehr.wisehr.common.ResponseDTO;
import com.wisehr.wisehr.schedule.dto.*;
import com.wisehr.wisehr.schedule.service.ScheduleService;
import com.wisehr.wisehr.setting.dto.SettingCareerDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
@Tag(name = "스케줄 관리 스웨거 연동")
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
 *  월로 조회
 *  조건에 따른 조회 포함
 */
@Tag(name = "스케줄 조회", description = "스케줄 전체 조회")
@PostMapping("/searchMonth")
public ResponseEntity<ResponseDTO> searchMonth(@RequestBody ScheduleSearchValueDTO value) {
    if(value.getYearMonth() == null){
    Date date = new Date();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
    System.out.println("simpleDateFormat = " + simpleDateFormat);
    String now = simpleDateFormat.format(date);
    value.setYearMonth(now);
    return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", scheduleService.searchMonth(value)));

    }   else {
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", scheduleService.searchMonth(value)));
    }
}

    /**
     *
     *  날짜로 조회
     */
    @Tag(name = "스케줄 조회", description = "스케줄 날짜로 조회")
    @PostMapping("/searchDay")
    public ResponseEntity<ResponseDTO> searchValue(@RequestBody ScheduleSearchValueDTO valueDTO) {
        System.out.println("valueDTO"+ valueDTO);

            return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", scheduleService.searchValue(valueDTO)));

    }


    /**
     * 2. 해당 날짜 누르면 뜨는 창
     * 그날의 근무그룹 별로 지정된 사람(searchValue 사용), 미등록된 사람 뜸(아래 메소드)
     */
//    @PostMapping("/notContain")
//    public ResponseEntity<ResponseDTO> notContain(@RequestBody ScheduleSearchValueDTO valueDTO) {
//        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", scheduleService.notContain(valueDTO)));
//
//    }

    /**
     * 2. 해당 날짜 누르면 뜨는 참
     * 부서별로 사람 검색(각 부서에 몇명이 있는지도 count)
     */
    @Tag(name = "스케줄 조회", description = "스케줄 특정 날짜의 부서, 인원 조회")
    @PostMapping("/SearchCountDepCode")
    public ResponseEntity<ResponseDTO> SearchCountDepCode() {
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", scheduleService.seachCountDepCode()));

    }

    /**
     * 3. 새 근무 편성 관련
     * 근무패턴 테이블 insert
     */
    @Tag(name = "스케줄 패턴 등록", description = "스케줄 패턴 등록")
    @PostMapping("/workPattern")
    public ResponseEntity<ResponseDTO> insertWorkPattern(@RequestBody ScheduleWorkPatternDTO patternDTO){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "근무패턴 등록 성공", scheduleService.insertWorkPattern(patternDTO)));
    }
    /**
     * 3.새 근무 편성 관련
     * 근무 패턴 수정
     */
    @Tag(name = "스케줄 패턴 수정", description = "스케줄 패턴 수정")
    @PutMapping(value = "/workPattern")
    public ResponseEntity<ResponseDTO> updateWorkPattern(@RequestBody ScheduleWorkPatternDTO patternDTO){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "근무패턴 수정 성공", scheduleService.updateWorkPattern(patternDTO)));
    }
    /**
     * 4. 새 근무 그룹 관련
     * 일정(schedule) 테이블 insert -> 근무 패턴 요일 테이블 insert
     */
    @Tag(name = "스케줄 근무그룹 등록", description = "스케줄 근무그룹 등록")
    @PostMapping("/schedule")
    public ResponseEntity<ResponseDTO> insertSchedule(@ModelAttribute ScheduleInsertDTO insertDTO){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "근무그룹 등록 성공", scheduleService.insertSchedule(insertDTO)));
    }

    /**
     * 4. 새 근무 그룹 관련
     * 일정 테이블, 근무 패턴 요일 테이블 수정
     */
    @Tag(name = "스케줄 근무 그룹 수정", description = "스케줄 근무 그룹 수정")
    @PutMapping(value = "/schedule")
    public ResponseEntity<ResponseDTO> updateSchedule(@ModelAttribute ScheduleInsertDTO insertDTO){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "근무그룹 수정 성공", scheduleService.updateSchedule(insertDTO)));
    }


    /**
     * 5. 새 근무 그룹 관련
     * 근무 그룹에 사람 넣으면
     * schedule_allowance에 insert
     */
    @Tag(name = "스케줄 근무 그룹에 인원 편성", description = "스케줄 근무 그룹에 인원 편성")
    @PostMapping("/allowance")
    public ResponseEntity<ResponseDTO> insertScheduleAllowance(@ModelAttribute ScheduleAllowanceDTO allowanceDTO) {
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "근무 그룹에 사람 등록 성공", scheduleService.insertScheduleAllowance(allowanceDTO)));
    }
    /**
     * 5. 새 근무 그룹 관련
     * 근무 그룹 인원 수정
     * schedule_allowance 수정
     */
    @Tag(name = "스케줄 근무 그룹에 인원 수정", description = "스케줄 근무 그룹에 인원 수정")
    @PutMapping(value = "/allowance")
    public ResponseEntity<ResponseDTO> updateScheduleAllowance(@RequestBody ScheduleAllowanceDTO allowanceDTO){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "근무 그룹에 사람 수정 성공", scheduleService.updateScheduleAllowance(allowanceDTO)));
    }

    /**
     * 6. 근무외 일정추가
     * etc_pattern 테이블에 insert
     */
    @Tag(name = "스케줄 패턴 근무외 일정 추가", description = "스케줄 패턴 근무외 일정 추가")
    @PostMapping("/etcPattern")
    public ResponseEntity<ResponseDTO> insertEtcPattern(@RequestBody ScheduleEtcPatternDTO etcPatternDTO){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "근무외 일정 등록 성공", scheduleService.insertEtcPattern(etcPatternDTO)));
    }

    /**
     * 6. 근무외 일정추가
     * etc_pattern 테이블 수정
     */
    @Tag(name = "스케줄 패턴 근무외 일정 수정", description = "스케줄 패턴 근무외 일정 수정")
    @PutMapping(value = "/etcPattern")
    public ResponseEntity<ResponseDTO> updateEtcPattern(@RequestBody ScheduleEtcPatternDTO etcPatternDTO){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "근무외 일정 수정 성공", scheduleService.updateEtcPattern(etcPatternDTO)));
    }


    @Tag(name = "스케줄 패턴 조회", description = "스케줄 패턴 조회")
    @GetMapping("/patternAndDaySearch")
    public ResponseEntity<ResponseDTO> patternAndDaySearch(){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", scheduleService.patternAndDaySearch()));
    }

    @GetMapping("/patternSearch")
    public ResponseEntity<ResponseDTO> patternSearch(){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", scheduleService.patternSearch()));
    }

    @GetMapping("/etcPattern")
    public ResponseEntity<ResponseDTO> etcPatternSearch(){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", scheduleService.etcPatternSearch()));
    }

    @PostMapping("/notContain")
    public ResponseEntity<ResponseDTO> notContain(@RequestBody ScheduleSearchValueDTO valueDTO) {
        System.out.println("valueDTO"+ valueDTO);

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", scheduleService.notContain(valueDTO)));

    }}

