package com.wisehr.wisehr.approval.repository;

import com.wisehr.wisehr.approval.entity.Approval;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ApprovalRepository extends JpaRepository<Approval, String> {
    List<Approval> findByPayCode(@Param("payCode") String payCode);
}
