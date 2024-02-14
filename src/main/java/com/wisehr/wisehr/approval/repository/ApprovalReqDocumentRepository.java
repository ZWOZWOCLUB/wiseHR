package com.wisehr.wisehr.approval.repository;

import com.wisehr.wisehr.approval.entity.ApprovalReqDocument;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApprovalReqDocumentRepository extends JpaRepository<ApprovalReqDocument, String> {

    ApprovalReqDocument findByApprovalPayCode(String payCode);
}
