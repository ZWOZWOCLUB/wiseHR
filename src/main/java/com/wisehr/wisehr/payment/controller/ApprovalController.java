package com.wisehr.wisehr.payment.controller;


import com.wisehr.wisehr.common.ResponseDTO;
import com.wisehr.wisehr.payment.dto.ApprovalCompleteDTO;
import com.wisehr.wisehr.payment.dto.ApprovalAnnualDTO;
import com.wisehr.wisehr.payment.service.ApprovalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Slf4j
@RequestMapping("approval")
public class ApprovalController {

    private final ApprovalService approvalService;

    public ApprovalController(ApprovalService approvalService) {
        this.approvalService = approvalService;
    }


    @GetMapping("/receivedpayment/{memCode}")      // payment페이지로 오면 바로 reqpayment로 오도록
    public ResponseEntity<ResponseDTO> reqPayment(@PathVariable Long memCode){
        log.info("memCodere : " + memCode);

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회성공", approvalService.selectReqPayment(memCode)));
    }

    @GetMapping("/sendpayment/{memCode}")
    public ResponseEntity<ResponseDTO> recPayment(@PathVariable Long memCode){
        log.info("memCodese : " + memCode);

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회 성공", approvalService.selectRecPayment(memCode)));
    }

    @PostMapping("/annual")
    public ResponseEntity<ResponseDTO> submitAnnual(@ModelAttribute ApprovalAnnualDTO annual, MultipartFile paymentFile){
        // paymentFile = > http에 업로드한 이미지 (값이 주소값 처럼 나온다.
        // annual => http에 넣은 값이 온다

        log.info("=== paymentFile : " + paymentFile );
        log.info("-==== paymentAnnual : " + annual);

        return ResponseEntity.ok()
                .body(new ResponseDTO(HttpStatus.OK, "연차 등록 성공", approvalService.submitAnnual(annual, paymentFile)));
    }

    @PutMapping(value = "/complete")
    public ResponseEntity<ResponseDTO> completeApproval(@ModelAttribute ApprovalCompleteDTO approval){

        log.info("approval : " + approval);

        return ResponseEntity.ok()
                .body(new ResponseDTO(HttpStatus.OK, "결재 완료", approvalService.completeApproval(approval)));
    }
}
