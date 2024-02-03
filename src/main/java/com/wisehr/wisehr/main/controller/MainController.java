package com.wisehr.wisehr.main.controller;

import com.wisehr.wisehr.common.ResponseDTO;
import com.wisehr.wisehr.main.service.MainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("main")
@Slf4j
public class MainController {

    private final MainService mainService;

    public MainController(MainService mainService) {
        this.mainService = mainService;
    }

    /**
     * 결재 승인 내역 조회(내역 중 승인, 대기, 반려의 개수)
     * @return
     */
    @GetMapping("/approvalCount/{memCode}")
    public ResponseEntity<ResponseDTO> selectApprovalCount(@PathVariable Long memCode){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", mainService.selectApprovalCount(memCode)));
    }
}
