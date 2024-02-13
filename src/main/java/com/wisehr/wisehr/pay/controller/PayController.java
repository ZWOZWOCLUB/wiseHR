package com.wisehr.wisehr.pay.controller;

import com.wisehr.wisehr.common.ResponseDTO;
import com.wisehr.wisehr.pay.service.PayService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "급여 관리 스웨거 연동")
@Slf4j
@RestController
@RequestMapping("pay")
public class PayController {
    private final PayService payService;


    public PayController(PayService payService) {
        this.payService = payService;
    }
    @Tag(name = "급여 조회", description = "급여 내역 전체 조회")
    @GetMapping("/payList/{memCode}/{yearMonth}")
    public ResponseEntity<ResponseDTO> payList(@PathVariable String yearMonth, @PathVariable String memCode){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", payService.selectPayList(yearMonth, memCode)));
    }
    @Tag(name = "급여 조회", description = "급여 내역 상세 조회")
    @GetMapping("/pay-List2/{memCode}")
    public ResponseEntity<ResponseDTO> payPage(@PathVariable int memCode){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", payService.searchYear(memCode)));
    }

    @Tag(name = "급여 조회", description = "입사년도부터 올해까지 년도 조회")
    @GetMapping("/hireYearList/{memCode}")
    public ResponseEntity<ResponseDTO> hireYearList(@PathVariable int memCode){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", payService.hireYearList(memCode)));
    }


}
