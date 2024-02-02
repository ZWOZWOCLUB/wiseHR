package com.wisehr.wisehr.util;

import com.wisehr.wisehr.payment.dto.ApprovalAttachmentDTO;
import com.wisehr.wisehr.payment.dto.ApprovalDTO;
import com.wisehr.wisehr.payment.entity.ApprovalAttachment;
import com.wisehr.wisehr.payment.entity.ApprovalMember;
import com.wisehr.wisehr.payment.repository.ApprovalAttachmentRepository;
import com.wisehr.wisehr.payment.repository.ApprovalMemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.modelmapper.ModelMapper;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
public class ApprovalUtils {

    private ModelMapper modelMapper;
    private ApprovalAttachmentRepository attachmentRepository;
    private ApprovalMemberRepository approvalMemberRepository;

    @Value("src/main/resources/static/")
    private String IMAGE_DIR;

    @Autowired
    public ApprovalUtils(ModelMapper modelMapper, ApprovalAttachmentRepository attachmentRepository, ApprovalMemberRepository approvalMemberRepository) {
        this.modelMapper = modelMapper;
        this.attachmentRepository = attachmentRepository;
        this.approvalMemberRepository = approvalMemberRepository;
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

            log.info("파일 이름 : " + approvalFile.getName());


            log.info("storyClass : " + story.getClass());

            log.info("story : " + story );


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

    public ApprovalMember roleMember(Long memberCode){
        //결재자를 찾기 위해서 결재 기안자와 같은 부서의 관리자급을 찾기.


        ApprovalMember memDep = approvalMemberRepository.findByMemCode(memberCode);

        log.info("memDep : " + memDep);

        List<ApprovalMember> roleMem = approvalMemberRepository.findByDepartmentDepCode(memDep.getDepartment().getDepCode());

        log.info("roleMem : " + roleMem);

        ApprovalMember depRole = new ApprovalMember();

        for (int i = 0; i < roleMem.size(); i++) {
            if (memDep.getMemRole().equals("일반사원")) {
                if (roleMem.get(i).getMemRole().contains("중간관리자")) {
                    depRole = roleMem.get(i);
                    log.info("일반사원의 결재 상신");
                }
            } else if (memDep.getMemRole().equals("중간관리자")) {
                if (roleMem.get(i).getMemRole().contains("최고관리자")) {
                    depRole = roleMem.get(i);
                    log.info("중간관리자의 결재 상신");
                }
            } else {
                depRole = memDep;
            }
        }

        log.info("depRole : " + depRole);

        return depRole;
    }
}
