package com.wisehr.wisehr.payment.repository;

import com.wisehr.wisehr.payment.entity.ApprovalPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ApprovalPaymentRepository extends JpaRepository<ApprovalPayment, String> {
    List<ApprovalPayment> findByPaymentMemberMemCode(Long memCode);
}
