package com.wisehr.wisehr.approval.repository;

import com.wisehr.wisehr.approval.entity.ApprovalPatternDay;
import com.wisehr.wisehr.approval.entity.ApprovalPatternDayPK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApprovalPatternDayRepository extends JpaRepository<ApprovalPatternDay, ApprovalPatternDayPK> {
    List<ApprovalPatternDay> findByApprovalPatternDayPK_WokCode(Long wokCode);
}
