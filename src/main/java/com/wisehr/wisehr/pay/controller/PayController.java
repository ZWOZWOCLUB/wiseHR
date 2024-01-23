package com.wisehr.wisehr.pay.controller;

import com.wisehr.wisehr.common.ResponseDTO;
import com.wisehr.wisehr.pay.service.PayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

//    @GetMapping("/payList")
//    public ResponseEntity<ResponseDTO> payList(){
//
//    }
}
