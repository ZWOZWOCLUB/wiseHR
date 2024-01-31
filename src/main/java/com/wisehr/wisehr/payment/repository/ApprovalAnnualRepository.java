package com.wisehr.wisehr.payment.repository;

import com.wisehr.wisehr.payment.entity.ApprovalAnnual;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApprovalAnnualRepository extends JpaRepository<ApprovalAnnual, String> {

    ApprovalAnnual findByApprovalPayCode(String payCode);
}
