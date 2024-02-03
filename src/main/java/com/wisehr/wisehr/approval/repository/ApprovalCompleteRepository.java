package com.wisehr.wisehr.approval.repository;

import com.wisehr.wisehr.approval.entity.ApprovalComplete;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApprovalCompleteRepository extends JpaRepository<ApprovalComplete, String> {
    List<ApprovalComplete> findByApprovalMemberMemCode(Long memCode);

    List<ApprovalComplete> findByApprovalApprovalMemberMemCode(Long memCode);
}
