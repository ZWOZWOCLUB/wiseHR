package com.wisehr.wisehr.approval.repository;

import com.wisehr.wisehr.approval.entity.ApprovalComplete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ApprovalCompleteRepository extends JpaRepository<ApprovalComplete, String> {

    List<ApprovalComplete> findByApprovalMemberMemCodeAndAppStateInOrderByApprovalPayDateDesc(Long memCode, List<String> includedAppStates);


    @Query("SELECT ac FROM ApprovalComplete ac WHERE ac.approval.approvalMember.memCode = :memCode ORDER BY CASE WHEN ac.appState = '대기' THEN 0 ELSE 1 END, ac.approval.payDate DESC")
    List<ApprovalComplete> findByApprovalApprovalMemberMemCodeOrderByAppStateAndApprovalPayDateDesc(@Param("memCode") Long memCode);


    @Query("SELECT ac FROM ApprovalComplete ac WHERE ac.approvalMember.memCode = :memCode ORDER BY CASE WHEN ac.appState = '대기' THEN 0 ELSE 1 END, ac.approval.payDate DESC")
    List<ApprovalComplete> findByApprovalMemberMemCodeOrderByAppStateAndApprovalPayDateDesc(@Param("memCode") Long memCode);
}
