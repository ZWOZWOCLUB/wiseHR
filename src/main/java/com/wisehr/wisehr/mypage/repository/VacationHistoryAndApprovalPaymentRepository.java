package com.wisehr.wisehr.mypage.repository;

import com.wisehr.wisehr.mypage.entity.VacationHistory;
import com.wisehr.wisehr.mypage.entity.VacationHistoryAndApprovalPayment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VacationHistoryAndApprovalPaymentRepository extends JpaRepository<VacationHistoryAndApprovalPayment, Integer> {


    List<VacationHistoryAndApprovalPayment> findByMemCode(int memCode);
}
