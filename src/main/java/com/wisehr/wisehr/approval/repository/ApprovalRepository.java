package com.wisehr.wisehr.approval.repository;

import com.wisehr.wisehr.approval.entity.Approval;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApprovalRepository extends JpaRepository<Approval, String> {

}
