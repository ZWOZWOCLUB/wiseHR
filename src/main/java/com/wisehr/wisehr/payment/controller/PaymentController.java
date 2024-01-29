package com.wisehr.wisehr.payment.controller;


import com.wisehr.wisehr.common.ResponseDTO;
import com.wisehr.wisehr.payment.dto.ApprovalPaymentDTO;
import com.wisehr.wisehr.payment.dto.PaymentAnnualDTO;
import com.wisehr.wisehr.payment.dto.PaymentAttachmentDTO;
import com.wisehr.wisehr.payment.dto.PaymentDTO;
import com.wisehr.wisehr.payment.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;

@RestController
@Slf4j
@RequestMapping("payment")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }


    @GetMapping("/reqpayment/{memCode}")      // payment페이지로 오면 바로 reqpayment로 오도록
    public ResponseEntity<ResponseDTO> reqPayment(@PathVariable Long memCode){
        log.info("memCode : " + memCode);

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회성공", paymentService.selectReqPayment(memCode)));
    }

    @PostMapping("/submit")
    public ResponseEntity<ResponseDTO> appPayment(@RequestBody ApprovalPaymentDTO payment){

//        payment.getPaymentMemberDTO().setMemCode(1L);
//        payment.setAppState("반려");
//        payment.setAppDate(Date.valueOf("2024-02-01"));
//        payment.getPayment().setPayCode("pay12");

        log.info("payment : " + payment);

        return ResponseEntity.ok()
                .body(new ResponseDTO(HttpStatus.OK, "결재 상신 완료", paymentService.appPayment(payment)));
    }

    @PostMapping("/annual")
    public ResponseEntity<ResponseDTO> submitAnnual(@ModelAttribute PaymentAttachmentDTO attachment, MultipartFile paymentFile){
        log.info("=== paymentFile : " + paymentFile );
        log.info("-==== paymentAnnual : " + attachment);

        return ResponseEntity.ok()
                .body(new ResponseDTO(HttpStatus.OK, "연차 등록 성공", paymentService.submitAnnual(attachment, paymentFile)));
    }
}
