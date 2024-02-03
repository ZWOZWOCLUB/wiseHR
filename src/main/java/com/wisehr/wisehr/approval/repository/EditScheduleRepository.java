package com.wisehr.wisehr.approval.repository;

import com.wisehr.wisehr.approval.entity.EditSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EditScheduleRepository extends JpaRepository<EditSchedule, String> {
    EditSchedule findByApprovalPayCode(String payCode);
}
