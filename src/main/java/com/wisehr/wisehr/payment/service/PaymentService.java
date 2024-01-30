package com.wisehr.wisehr.payment.service;


import com.wisehr.wisehr.payment.dto.ApprovalPaymentDTO;
import com.wisehr.wisehr.payment.dto.PaymentAnnualDTO;
import com.wisehr.wisehr.payment.dto.PaymentAttachmentDTO;
import com.wisehr.wisehr.payment.dto.PaymentDTO;
import com.wisehr.wisehr.payment.entity.ApprovalPayment;
import com.wisehr.wisehr.payment.entity.Payment;
import com.wisehr.wisehr.payment.entity.PaymentAnnual;
import com.wisehr.wisehr.payment.entity.PaymentAttachment;
import com.wisehr.wisehr.payment.repository.ApprovalPaymentRepository;
import com.wisehr.wisehr.payment.repository.PaymentAnnualRepository;
import com.wisehr.wisehr.payment.repository.PaymentAttachmentRepository;
import com.wisehr.wisehr.payment.repository.PaymentRepository;
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
    private final PaymentRepository paymentRepository;
    private final PaymentAttachmentRepository attachmentRepository;
    private final PaymentAnnualRepository paymentAnnualRepository;
    private final ModelMapper modelMapper;

    // 이미지 저장 위치 및 응답 할 이미지 주소 (여기 뒤에 /{memCode} 넣으면 됩니다!!
    // 위아래 둘 다!! 동적으로)
    @Value("src/main/resources/static/memberFiles/")
    private String IMAGE_DIR;

    @Value("http://localhost:8001/attachmentFiles/")
    private String IMAGE_URL;

    public PaymentService(ApprovalPaymentRepository approvalPaymentRepository, PaymentRepository paymentRepository, PaymentAttachmentRepository attachmentRepository, PaymentAnnualRepository paymentAnnualRepository, ModelMapper modelMapper) {
        this.approvalPaymentRepository = approvalPaymentRepository;
        this.paymentRepository = paymentRepository;
        this.attachmentRepository = attachmentRepository;
        this.paymentAnnualRepository = paymentAnnualRepository;
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

    @Transactional
    public String submitAnnual(PaymentAnnualDTO annual, MultipartFile paymentFile) {

        log.info("===== attchment start : " + paymentFile );
        log.info("-===== paymentAnnual : " + annual);

        String path = IMAGE_DIR + annual.getPayment().getPaymentMember().getMemCode();
        // path는 기존 IMAGE_DIR에 사번을 +해줌으로써 사번으로 폴더가 생성된다.

        PaymentAttachmentDTO att = new PaymentAttachmentDTO();
        // 엔티티로 바꿔서 DB에 넣어주기 위해 DTO에 값을 설정하는 과정

        att.setPayAtcPath(path);
        att.setPayAtcName(paymentFile.getName());
        att.setPayment(annual.getPayment());

        PaymentDTO pay = annual.getPayment();


        String story = null;

        log.info("path : " + path);

        int result = 0 ;

        try {
            story = FileUploadUtils.saveFile(path, paymentFile.getName(), paymentFile);



            Payment payment = modelMapper.map(pay, Payment.class);

            paymentRepository.save(payment);

            log.info("payment : " + payment );

            PaymentAnnual atcment = modelMapper.map(annual, PaymentAnnual.class);

            log.info("atcment : " + atcment );

            paymentAnnualRepository.save(atcment);

            log.info("story : " + story);

            PaymentAttachment atts = modelMapper.map(att, PaymentAttachment.class);
            // 위에서 값을 설정해준 att에 Mapper를 통해 엔티티로 전환

            log.info("atts : " + atts);

            attachmentRepository.save(atts);
            // Repository를 통해 DB에 저장!

            result =1;
        } catch (IOException e) {
            log.info("실패~");
        }


        return (result > 0)? "성공" : "나가";
    }
}
