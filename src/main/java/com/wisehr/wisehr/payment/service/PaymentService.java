package com.wisehr.wisehr.payment.service;


import com.wisehr.wisehr.payment.dto.ApprovalPaymentDTO;
import com.wisehr.wisehr.payment.dto.PaymentAttachmentDTO;
import com.wisehr.wisehr.payment.entity.ApprovalPayment;
import com.wisehr.wisehr.payment.entity.PaymentAttachment;
import com.wisehr.wisehr.payment.repository.ApprovalPaymentRepository;
import com.wisehr.wisehr.payment.repository.PaymentAttachmentRepository;
import com.wisehr.wisehr.util.FileUploadUtils;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PaymentService {

    private final ApprovalPaymentRepository approvalPaymentRepository;
    private final PaymentAttachmentRepository attachmentRepository;
    private final ModelMapper modelMapper;

    // 이미지 저장 위치 및 응답 할 이미지 주소 (여기 뒤에 /{memCode} 넣으면 됩니다!!
    // 위아래 둘 다!! 동적으로)
    @Value("src/main/resources/static/memberFiles/")
    private String IMAGE_DIR;

    @Value("http://localhost:8001/attachmentFiles/")
    private String IMAGE_URL;

    public PaymentService(ApprovalPaymentRepository approvalPaymentRepository, PaymentAttachmentRepository attachmentRepository, ModelMapper modelMapper) {
        this.approvalPaymentRepository = approvalPaymentRepository;
        this.attachmentRepository = attachmentRepository;
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

    @Transactional
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

    public String submitAnnual(PaymentAttachmentDTO attachment, MultipartFile paymentFile) {

        log.info("===== attchment start : " + paymentFile );
        log.info("-===== paymentAnnual : " + attachment);

        String path = IMAGE_DIR +attachment.getPayment().getPaymentMember().getMemCode();

        String story = null;

        log.info("path : " + path);

        int result = 0 ;

        try {
            story = FileUploadUtils.saveFile(path, paymentFile.getName(), paymentFile);

            PaymentAttachment atcment = modelMapper.map(attachment, PaymentAttachment.class);

            atcment.setPayAtcPath(path);
            atcment.setPayAtcName(paymentFile.getName());
            atcment.getPayment().setPayCode(String.valueOf(attachment.getPayment().getPaymentMember().getMemCode()));
            log.info("atcment : " + atcment);
            attachmentRepository.save(atcment);

            result =1;
        } catch (IOException e) {
            log.info("실패~");
        }


        return (result > 0)? "성공" : "나가";
    }
}
