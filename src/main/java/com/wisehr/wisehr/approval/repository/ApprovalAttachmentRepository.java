package com.wisehr.wisehr.approval.repository;

import com.wisehr.wisehr.approval.entity.ApprovalAttachment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApprovalAttachmentRepository extends JpaRepository<ApprovalAttachment,String> {
    List<ApprovalAttachment> findByApprovalPayCode(String payCode);

}
