package com.wisehr.wisehr.approval.repository;

import com.wisehr.wisehr.approval.entity.ApprovalAnnual;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ApprovalAnnualRepository extends JpaRepository<ApprovalAnnual, String> {

    ApprovalAnnual findByApprovalPayCode(String payCode);

    @Query("SELECT v FROM ApprovalAnnual v " +
            "WHERE (v.vacStartDate >= :startDate AND v.vacStartDate <= :endDate) " +
            "   OR (v.vacEndDate >= :startDate AND v.vacEndDate <= :endDate) " +
            "   OR (:startDate >= v.vacStartDate AND :startDate <= v.vacEndDate) " +
            "   OR (:endDate >= v.vacStartDate AND :endDate <= v.vacEndDate)")
    List<ApprovalAnnual> findOverlappingVacations(@Param("startDate") String startDate, @Param("endDate") String endDate);
}
