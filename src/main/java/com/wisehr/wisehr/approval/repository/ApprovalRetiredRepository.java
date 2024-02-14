package com.wisehr.wisehr.approval.repository;

import com.wisehr.wisehr.approval.entity.ApprovalRetired;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApprovalRetiredRepository extends JpaRepository<ApprovalRetired, String> {
    ApprovalRetired findByApprovalPayCode(String payCode);
}

