package com.wisehr.wisehr.schedule.controller;

import com.wisehr.wisehr.common.ResponseDTO;
import com.wisehr.wisehr.schedule.service.ScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;

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

/**
 * 일정 조회(첫페이지에 전체 일정이 다 뜨게 이번달꺼만)
 */

/**
 *
 *일정조회 (년-월로 조회 여기는 schedule테이블이 아닌 attendance 테이블로 가져옴)
 */
@GetMapping("/searchDate/{date}")
    public ResponseEntity<ResponseDTO> searchDate(@PathVariable Date date){
    return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", scheduleService.searchDate(date)));
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
