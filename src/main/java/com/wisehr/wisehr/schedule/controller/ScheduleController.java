package com.wisehr.wisehr.schedule.controller;

import com.wisehr.wisehr.common.ResponseDTO;
import com.wisehr.wisehr.schedule.dto.*;
import com.wisehr.wisehr.schedule.service.ScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
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
 * 일정 등록
 */
@PostMapping("/insertSchedule")
public ResponseEntity<ResponseDTO> insertSchedule(@RequestBody SchedulePatternInsertDTO insertDTO
) {
    System.out.println("insertDTO = " + insertDTO);
    return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "일정 패턴 등록 성공", scheduleService.insertSchedule(insertDTO)));
}




/**
 * 일정 조회(첫페이지에 전체 일정이 다 뜨게 이번달꺼만)
 */

/**
 *
 *이전 스케줄 조회 (년-월로 조회 schedule테이블이 아닌 attendance 테이블로 가져옴)
 */
@GetMapping("/searchPrev/{yearMonth}")
    public ResponseEntity<ResponseDTO> searchPrev(@PathVariable String yearMonth){
    return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", scheduleService.searchPrev(yearMonth)));
}

/**
 *선택한 다른 사람의 일정, 년 - 월 조회
 * /


 /**
 *일정 패턴 등록
 */


/**
 *일정 패턴 수정
 * */


/**
 *일정 패턴 삭제(삭제여부 테이블에 값 Y로 업데이트)
 * */


/**
 *일정 패턴에 직원 넣기
 * */

/**
 *근무 일정 - 패턴 출력
 * */

/**
 *근무 일정 - 수정된 일정 추가*
 * */
}
