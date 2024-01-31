package com.wisehr.wisehr.util;

import com.wisehr.wisehr.payment.dto.ApprovalAttachmentDTO;
import com.wisehr.wisehr.payment.dto.ApprovalDTO;
import com.wisehr.wisehr.payment.entity.ApprovalAttachment;
import com.wisehr.wisehr.payment.repository.ApprovalAttachmentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.modelmapper.ModelMapper;

import java.io.IOException;

@Slf4j
@Component
public class ApprovalFileUtils {

    private ModelMapper modelMapper;
    private ApprovalAttachmentRepository attachmentRepository;

    @Value("src/main/resources/static/")
    private String IMAGE_DIR;

    @Autowired
    public ApprovalFileUtils(ModelMapper modelMapper, ApprovalAttachmentRepository attachmentRepository) {
        this.modelMapper = modelMapper;
        this.attachmentRepository = attachmentRepository;
    }


    public void fileClear(ApprovalDTO approval, MultipartFile approvalFile){


        try {
            String path = IMAGE_DIR + "memberFiles/" + approval.getApprovalMember().getMemCode();
            // path는 기존 IMAGE_DIR에 사번을 +해줌으로써 사번으로 폴더가 생성된다.

            log.info("path : " + path);

            String story = null;

            ApprovalAttachmentDTO att = new ApprovalAttachmentDTO();
            // 엔티티로 바꿔서 DB에 넣어주기 위해 DTO에 값을 설정하는 과정

            att.setPayAtcPath(path);
            att.setPayAtcName(approvalFile.getName());
            att.setApproval(approval);
            att.setPayAtcDeleteStatus("N");

            log.info("att : " + att);


            story = FileUploadUtils.saveFile(path, approvalFile.getName(), approvalFile);


            log.info("story : " + story);


            ApprovalAttachment atts = modelMapper.map(att, ApprovalAttachment.class);
            // 위에서 값을 설정해준 att에 Mapper를 통해 엔티티로 전환

            log.info("atts : " + atts);

            attachmentRepository.save(atts);

            log.info("첨부파일 성공 ");
        } catch (IOException e) {
            e.printStackTrace();
            log.info("첨부파일 실패");
        }

        log.info("ㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋ했다!!!!");

    }
}
