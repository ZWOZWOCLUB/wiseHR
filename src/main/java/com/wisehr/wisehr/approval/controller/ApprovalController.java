package com.wisehr.wisehr.approval.controller;


import com.wisehr.wisehr.attendance.dto.Attendance2DTO;
import com.wisehr.wisehr.common.Criteria;
import com.wisehr.wisehr.common.PageDTO;
import com.wisehr.wisehr.common.PagingResponseDTO;
import com.wisehr.wisehr.common.ResponseDTO;
import com.wisehr.wisehr.approval.dto.*;
import com.wisehr.wisehr.approval.service.ApprovalService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("approval")
@Tag(name = "결재 관련 스웨거 연동")
public class ApprovalController {

    private final ApprovalService approvalService;

    public ApprovalController(ApprovalService approvalService) {
        this.approvalService = approvalService;
    }


    // 받은 결재 조회
    @GetMapping("/receivedapproval/{memCode}")      // payment페이지로 오면 바로 reqpayment로 오도록
    @Tag(name = "받은 결재 조회", description = "받은 결재 조회")
    public ResponseEntity<ResponseDTO> reqPayment(@PathVariable Long memCode,
                                                  @RequestParam(name = "offset", defaultValue = "1") String offset){
        log.info("offset : " + offset);
        log.info("memCodere : " + memCode);

        Criteria cri = new Criteria(Integer.valueOf(offset), 10);

        PagingResponseDTO pagingResponseDTO = new PagingResponseDTO();

        Page<ApprovalCompleteDTO> approvalList = approvalService.selectReqPayment(memCode, cri);

        pagingResponseDTO.setData(approvalList);

        pagingResponseDTO.setPageInfo(new PageDTO(cri, (int) approvalList.getTotalElements()));

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회성공", pagingResponseDTO));
    }

    // 보낸 결재 조회
    @GetMapping("/sendapproval/{memCode}")
    @Tag(name = "보낸 결재 조회", description = "보낸 결재 조회")
    public ResponseEntity<ResponseDTO> recPayment(@PathVariable Long memCode ,
                                                  @RequestParam(name = "offset", defaultValue = "1") String offset){
        Criteria cri = new Criteria(Integer.valueOf(offset), 10);

        PagingResponseDTO pagingResponseDTO = new PagingResponseDTO();

        Page<ApprovalCompleteDTO> approvalList = approvalService.selectRecPayment(memCode , cri);

        pagingResponseDTO.setData(approvalList);

        pagingResponseDTO.setPageInfo(new PageDTO(cri, (int) approvalList.getTotalElements()));

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회성공", pagingResponseDTO));

    }

    // 결재 조회
    @GetMapping("/approval/{payCode}")
    @Tag(name = "결재 내용 조회", description = "결재 내용 조회")
    public ResponseEntity<ResponseDTO> approvalDetail(@PathVariable String payCode){

        log.info("payCode : " + payCode);

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회성공", approvalService.selectApproval(payCode)));
    }

    @GetMapping("/approvalcomplete/{payCode}")
    @Tag(name = "결재 완료 조회", description = "결재 완료 조회")
    public ResponseEntity<ResponseDTO> approvalComplete(@PathVariable String payCode){
        log.info("payCode : " + payCode);

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회성공", approvalService.selectApprovalComplete(payCode)));
    }

    @GetMapping("/approvalattachment/{payCode}")
    @Tag(name = "결재 첨부파일 조회", description = "결재 첨부파일 조회")
    public ResponseEntity<ResponseDTO> approvalAttachment(@PathVariable String payCode){
        log.info("payCode : " + payCode);

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회성공", approvalService.selectApprovalAttachment(payCode)));
    }

    @GetMapping("/approvaltype/{payCode}")
    @Tag(name = "결재 타입 조회", description = "결재 타입 조회")
    public ResponseEntity<ResponseDTO> approvalType(@PathVariable String payCode){

        log.info("payCode : " + payCode);

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "조회성공", approvalService.selectApprovalType(payCode)));
    }

    // 연차 신청 상신
    @PostMapping("/annual")
    @Tag(name = "연차 결재 상신", description = "연차 결재 상신")
    public ResponseEntity<ResponseDTO> submitAnnual(@ModelAttribute ApprovalAnnual2DTO annual, MultipartFile approvalFile){
        // paymentFile = > http에 업로드한 이미지 (값이 주소값 처럼 나온다.
        // annual => http에 넣은 값이 온다

        log.info("=== approvalFile : " + approvalFile );
        log.info("-==== approvalAnnual : " + annual);

        return ResponseEntity.ok()
                .body(new ResponseDTO(HttpStatus.OK, "연차 등록 성공", approvalService.submitAnnual(annual, approvalFile)));
    }

    // 출퇴근 정정 신청 상신
    @PostMapping("/commute")
    @Tag(name = "출퇴근 정정 결재 상신", description = "출퇴근 정정 결재 상신")
    public ResponseEntity<ResponseDTO> submitCommute(@ModelAttribute EditCommute2DTO edit, MultipartFile approvalFile){

        log.info("edit : " + edit);
        log.info("file : " + approvalFile);


        return ResponseEntity.ok()
                .body(new ResponseDTO(HttpStatus.OK, "출퇴근 정정 성공", approvalService.submitCommute(edit, approvalFile)));

    }

    //스케줄 정정 신청 상신
    @PostMapping("/schedule")
    @Tag(name = "스케줄 정정 결재 상신", description = "스케줄 정정 결재 상신")
    public ResponseEntity<ResponseDTO> submitSchedule(@ModelAttribute EditSchedule2DTO schedule, MultipartFile approvalFile){

        log.info("edit : " + schedule);
        log.info("file : " + approvalFile);

        return ResponseEntity.ok()
                .body(new ResponseDTO(HttpStatus.OK, "스케줄 정정 성공", approvalService.submitSchedule(schedule, approvalFile)));

    }

    // 서류 요청 상신
    @PostMapping("/requestdocument")
    @Tag(name = "서류 요청 결재 상신", description = "서류 요청 결재 상신")
    public ResponseEntity<ResponseDTO> submitReqDoucument(@ModelAttribute ApprovalReqDocument2DTO reqDocument, MultipartFile approvalFile){

        log.info("reqDocument : " + reqDocument);
        log.info("file : " + approvalFile);

        return ResponseEntity.ok()
                .body(new ResponseDTO(HttpStatus.OK, "퇴직 신청 성공", approvalService.submitReqDocumnet(reqDocument, approvalFile)));

    }

    //퇴직 신청 상신
    @PostMapping("/retired")
    @Tag(name = "퇴직 요청 결재 상신", description = "퇴직 요청 결재 상신")
    public ResponseEntity<ResponseDTO> submitRetired(@ModelAttribute ApprovalRetired2DTO retired, MultipartFile approvalFile){

        log.info("retired : " + retired);
        log.info("file : " + approvalFile);

        return ResponseEntity.ok()
                .body(new ResponseDTO(HttpStatus.OK, "퇴직 신청 성공", approvalService.submitRetired(retired, approvalFile)));

    }

    // 결재 승인 로직
    @PutMapping(value = "/complete")
    @Tag(name = "결재 승인", description = "결재 승인")
    public ResponseEntity<ResponseDTO> completeApproval(@RequestBody ApprovalCompleteDTO approval){

        log.info("approval : " + approval);

        return ResponseEntity.ok()
                .body(new ResponseDTO(HttpStatus.OK, "결재 완료", approvalService.completeApproval(approval)));
    }


    //전결자 지정 로직
    @PutMapping(value = "/roleupdate")
    @Tag(name = "전결자 지정", description = "전결자 지정")
    public ResponseEntity<ResponseDTO> updateRole(@RequestBody ApproverProxy2DTO proxy1){

        log.info("proxy : " + proxy1);
        ApproverProxyDTO proxy = new ApproverProxyDTO();
        ApprovalMemberDTO member1 = new ApprovalMemberDTO();
        member1.setMemCode(Long.parseLong(proxy1.getRoleMember()));
        ApprovalProxyMemberDTO member2 = new ApprovalProxyMemberDTO();
        member2.setMemCode(Long.parseLong(proxy1.getProMember()));


        proxy.setProEndDate(proxy1.getProEndDate());
        proxy.setProStartDate(proxy1.getProStartDate());
        proxy.setRoleMember(member1);
        proxy.setProMember(member2);
        proxy.setProMemRole(proxy1.getProMemRole());


        return ResponseEntity.ok()
                .body(new ResponseDTO(HttpStatus.OK, "전결자 지정 완료", approvalService.updateRole(proxy)));
    }

    // 전결자 복구

    @PutMapping(value = "/rolerecovery")
    @Tag(name = "지정된 전결자 권한 복구", description = "지정된 전결자 권한 복구")
    public ResponseEntity<ResponseDTO> recoveryRole(@RequestBody Attendance2DTO attendance2DTO){

        log.info("requestBody : " + attendance2DTO);

        log.info("long : " + attendance2DTO);
        return ResponseEntity.ok()
                .body(new ResponseDTO(HttpStatus.OK, "전결자 지정 완료", approvalService.recoveryRole(attendance2DTO)));
    }

    // 전결자 찾아오는 과정
    @GetMapping(value = "/{proStartDate}/date/{proEndDate}/datte/{roleMemCode}")
    @Tag(name = "전결자 지정을 위한 날짜 조회", description = "전결자 지정을 위한 날짜 조회")
    public ResponseEntity<ResponseDTO> dateSearch(@PathVariable String proStartDate ,
                                                  @PathVariable String proEndDate,
                                                  @PathVariable String roleMemCode){

        ApprovalDateDTO date = new ApprovalDateDTO();

        date.setProStartDate(proStartDate);
        date.setProEndDate(proEndDate);
        date.setMemCode(Long.parseLong(roleMemCode));


        log.info("date : " + date );

        return  ResponseEntity.ok()
                .body(new ResponseDTO(HttpStatus.OK, "검색완료", approvalService.searchDate(date)));
    }

    /**
     * 로그인한 직원의 코드로 proxy_approver 조회, pro_mem_code의 Roll을 수정
     */
    @PostMapping(value = "/findProxyApprover")
    public ResponseEntity<ResponseDTO> findProxyApprover(@RequestBody Attendance2DTO attendance2DTO){

        return ResponseEntity.ok()
                .body(new ResponseDTO(HttpStatus.OK, "조회 완료", approvalService.findProxyApprover(attendance2DTO)));
    }

}
