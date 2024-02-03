package com.wisehr.wisehr.approval.repository;

import com.wisehr.wisehr.approval.entity.EditCommute;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EditCommuteRepository extends JpaRepository<EditCommute, String> {
    EditCommute findByApprovalPayCode(String payCode);
}
