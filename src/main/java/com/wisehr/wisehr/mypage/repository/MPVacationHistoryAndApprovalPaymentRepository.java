package com.wisehr.wisehr.mypage.repository;

import com.wisehr.wisehr.mypage.entity.MPVacationHistoryAndApprovalPayment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MPVacationHistoryAndApprovalPaymentRepository extends JpaRepository<MPVacationHistoryAndApprovalPayment, Integer> {


    List<MPVacationHistoryAndApprovalPayment> findByMemCode(int memCode);

    List<MPVacationHistoryAndApprovalPayment> findByMemCodeOrderByVhiCodeDesc(int memCode);

}
