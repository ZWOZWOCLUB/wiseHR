package com.wisehr.wisehr.pay.controller;

import com.wisehr.wisehr.common.ResponseDTO;
import com.wisehr.wisehr.pay.service.PayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("pay")
public class PayController {
    private final PayService payService;


    public PayController(PayService payService) {
        this.payService = payService;
    }

    @GetMapping("/payList/{memCode}/{year}")
    public ResponseEntity<ResponseDTO> payList(@PathVariable String year, @PathVariable String memCode){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", payService.selectPayList(year, memCode)));
    }

    @GetMapping("/pay-List2/{memCode}")
    public ResponseEntity<ResponseDTO> payPage(@PathVariable int memCode){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", payService.searchYear(memCode)));
    }

    @GetMapping("/hireYearList/{memCode}")
    public ResponseEntity<ResponseDTO> hireYearList(@PathVariable int memCode){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", payService.hireYearList(memCode)));
    }


}
