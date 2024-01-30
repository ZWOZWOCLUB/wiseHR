package com.wisehr.wisehr.payment.service;


import com.wisehr.wisehr.payment.dto.ApprovalAttachmentDTO;
import com.wisehr.wisehr.payment.dto.ApprovalCompleteDTO;
import com.wisehr.wisehr.payment.dto.ApprovalAnnualDTO;
import com.wisehr.wisehr.payment.dto.ApprovalDTO;
import com.wisehr.wisehr.payment.entity.ApprovalComplete;
import com.wisehr.wisehr.payment.entity.Approval;
import com.wisehr.wisehr.payment.entity.ApprovalAnnual;
import com.wisehr.wisehr.payment.entity.ApprovalAttachment;
import com.wisehr.wisehr.payment.repository.ApprovalCompleteRepository;
import com.wisehr.wisehr.payment.repository.ApprovalAnnualRepository;
import com.wisehr.wisehr.payment.repository.ApprovalAttachmentRepository;
import com.wisehr.wisehr.payment.repository.ApprovalRepository;
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
public class ApprovalService {

    private final ApprovalCompleteRepository approvalCompleteRepository;
    private final ApprovalRepository approvalRepository;
    private final ApprovalAttachmentRepository attachmentRepository;
    private final ApprovalAnnualRepository approvalAnnualRepository;
    private final ModelMapper modelMapper;

    // 이미지 저장 위치 및 응답 할 이미지 주소 (여기 뒤에 /{memCode} 넣으면 됩니다!!
    // 위아래 둘 다!! 동적으로)
    @Value("src/main/resources/static/memberFiles/")
    private String IMAGE_DIR;

    @Value("http://localhost:8001/attachmentFiles/")
    private String IMAGE_URL;

    public ApprovalService(ApprovalCompleteRepository approvalCompleteRepository, ApprovalRepository approvalRepository, ApprovalAttachmentRepository attachmentRepository, ApprovalAnnualRepository approvalAnnualRepository, ModelMapper modelMapper) {
        this.approvalCompleteRepository = approvalCompleteRepository;
        this.approvalRepository = approvalRepository;
        this.attachmentRepository = attachmentRepository;
        this.approvalAnnualRepository = approvalAnnualRepository;
        this.modelMapper = modelMapper;
    }


    public List<ApprovalCompleteDTO> selectReqPayment(Long memCode) {
        log.info("select 받은결재 memCode : " + memCode);
        log.info("memCode : " + memCode.getClass());



        List<ApprovalComplete> paymentList = approvalCompleteRepository.findByPaymentMemberMemCode(memCode);
        log.info("여기?");

        List<ApprovalCompleteDTO> payment = paymentList.stream()
                .map(paymt -> modelMapper.map(paymt, ApprovalCompleteDTO.class))
                .collect(Collectors.toList());

        log.info("완료 payment : " + payment);

        return payment;
    }


    public List<ApprovalCompleteDTO> selectRecPayment(Long memCode) {
        log.info("reqPayment Service Start : " + memCode);

//        List<ApprovalComplete> payment = approvalCompleteRepository.findByPaymentMemberMem

        return null;
    }

    @Transactional
    public String appPayment(ApprovalCompleteDTO payment) {

        int result = 0;
        try {
            log.info("서비스 payment : " + payment);

            ApprovalComplete submitPayment = modelMapper.map(payment, ApprovalComplete.class);

            log.info("submitPayment : " + submitPayment);

            approvalCompleteRepository.save(submitPayment);

            result = 1;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return (result > 0 ) ? "굿" : "나가";
    }

    @Transactional
    public String submitAnnual(ApprovalAnnualDTO annual, MultipartFile paymentFile) {

        log.info("===== attchment start : " + paymentFile );
        log.info("-===== paymentAnnual : " + annual);

        String path = IMAGE_DIR + annual.getPayment().getPaymentMember().getMemCode();
        // path는 기존 IMAGE_DIR에 사번을 +해줌으로써 사번으로 폴더가 생성된다.

        ApprovalAttachmentDTO att = new ApprovalAttachmentDTO();
        // 엔티티로 바꿔서 DB에 넣어주기 위해 DTO에 값을 설정하는 과정

        att.setPayAtcPath(path);
        att.setPayAtcName(paymentFile.getName());
        att.setPayment(annual.getPayment());

        ApprovalDTO pay = annual.getPayment();


        String story = null;

        log.info("path : " + path);

        int result = 0 ;

        try {
            story = FileUploadUtils.saveFile(path, paymentFile.getName(), paymentFile);



            Approval approval = modelMapper.map(pay, Approval.class);

            approvalRepository.save(approval);

            log.info("approval : " + approval);

            ApprovalAnnual atcment = modelMapper.map(annual, ApprovalAnnual.class);

            log.info("atcment : " + atcment );

            approvalAnnualRepository.save(atcment);

            log.info("story : " + story);

            ApprovalAttachment atts = modelMapper.map(att, ApprovalAttachment.class);
            // 위에서 값을 설정해준 att에 Mapper를 통해 엔티티로 전환

            log.info("atts : " + atts);

            attachmentRepository.save(atts);
            // Repository를 통해 DB에 저장!

            result =1;
        } catch (IOException e) {
            log.info("실패~");
        }


        return (result > 0)? "성공" : "실패";
    }


}
