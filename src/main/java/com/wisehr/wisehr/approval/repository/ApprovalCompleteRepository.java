package com.wisehr.wisehr.approval.repository;

import com.wisehr.wisehr.approval.entity.ApprovalComplete;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    List<ApprovalComplete> findByApprovalPayCode(String payCode);

    List<ApprovalComplete> findByApprovalApprovalMemberMemCodeAndAppState(Long memCode, String 승인);

    @Query("SELECT ac FROM ApprovalComplete ac WHERE ac.approvalMember.memCode = :memCode ORDER BY CASE WHEN ac.appState = '대기' THEN 0 ELSE 1 END, ac.approval.payDate DESC")
    Page<ApprovalComplete> findByApprovalMemberMemCode(Long memCode, Pageable paging);


    @Query("SELECT ac FROM ApprovalComplete ac WHERE ac.approval.approvalMember.memCode = :memCode ORDER BY CASE WHEN ac.appState = '대기' THEN 0 ELSE 1 END, ac.approval.payDate DESC")
    Page<ApprovalComplete> findByApprovalApprovalMemberMemCode(Long memCode, Pageable paging);

@Query("SELECT a FROM ApprovalComplete a " +
        "LEFT JOIN FETCH a.approval b " +
        "LEFT JOIN FETCH a.approvalMember c " +
        "WHERE a.approvalMember.memCode = :memCode " +
        "  AND (:status IS NULL OR a.appState = :status) " +
        "  AND (:start IS NULL OR b.payDate = :start) " +
        "  AND (:name IS NULL OR b.payName LIKE CONCAT('%', :name, '%')) " +
        "  AND (:type IS NULL OR b.payKind = :type )"+
        "ORDER BY CASE WHEN a.appState = '대기' THEN 0 ELSE 1 END, b.payDate DESC")
    Page<ApprovalComplete> findByApprovalMemberMemCode(Long memCode, String start, String name, String status, String type,Pageable paging);

    @Query("SELECT a FROM ApprovalComplete a " +
            "LEFT JOIN FETCH a.approval b " +
            "LEFT JOIN FETCH a.approvalMember c " +
            "WHERE b.approvalMember.memCode = :memCode " +
            "  AND (:status IS NULL OR a.appState = :status) " +
            "  AND (:start IS NULL OR b.payDate = :start) " +
            "  AND (:name IS NULL OR b.payName LIKE CONCAT('%', :name, '%')) " +
            "  AND (:type IS NULL OR b.payKind = :type )"+
            "ORDER BY CASE WHEN a.appState = '대기' THEN 0 ELSE 1 END, b.payDate DESC")
    Page<ApprovalComplete> findByApprovalApprovalMemberMemCodee(Long memCode, String start, String name, String status, String type, Pageable paging);
}
