package com.wisehr.wisehr.payment.service;


import com.wisehr.wisehr.payment.dto.ApprovalPaymentDTO;
import com.wisehr.wisehr.payment.entity.ApprovalPayment;
import com.wisehr.wisehr.payment.repository.ApprovalPaymentRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PaymentService {

    private final ApprovalPaymentRepository approvalPaymentRepository;
    private final ModelMapper modelMapper;

    public PaymentService(ApprovalPaymentRepository approvalPaymentRepository, ModelMapper modelMapper) {
        this.approvalPaymentRepository = approvalPaymentRepository;
        this.modelMapper = modelMapper;
    }


    public List<ApprovalPaymentDTO> selectReqPayment(Long memCode) {
        log.info("select 받은결재 memCode : " + memCode);
        log.info("memCode : " + memCode.getClass());



        List<ApprovalPayment> paymentList = approvalPaymentRepository.findByPaymentMemberMemCode(memCode);
        log.info("여기?");

        List<ApprovalPaymentDTO> payment = paymentList.stream()
                .map(paymt -> modelMapper.map(paymt, ApprovalPaymentDTO.class))
                .collect(Collectors.toList());

        log.info("완료 payment : " + payment);

        return payment;
    }

    public String appPayment(ApprovalPaymentDTO payment) {

        int result = 0;
        try {
            log.info("서비스 payment : " + payment);

            ApprovalPayment submitPayment = modelMapper.map(payment, ApprovalPayment.class);

            log.info("submitPayment : " + submitPayment);

            approvalPaymentRepository.save(submitPayment);

            result = 1;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return (result > 0 ) ? "굿" : "나가";
    }
}
