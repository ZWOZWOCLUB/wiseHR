package com.wisehr.wisehr.payment.repository;

import com.wisehr.wisehr.payment.entity.ApprovalComplete;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApprovalCompleteRepository extends JpaRepository<ApprovalComplete, String> {
    List<ApprovalComplete> findByPaymentMemberMemCode(Long memCode);
}
