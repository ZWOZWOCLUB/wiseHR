package com.wisehr.wisehr.approval.repository;

import com.wisehr.wisehr.approval.entity.ApprovalAnnual;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApprovalAnnualRepository extends JpaRepository<ApprovalAnnual, String> {

    ApprovalAnnual findByApprovalPayCode(String payCode);
}
