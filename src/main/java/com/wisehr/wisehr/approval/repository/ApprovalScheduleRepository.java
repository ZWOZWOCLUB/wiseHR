package com.wisehr.wisehr.approval.repository;

import com.wisehr.wisehr.approval.entity.ApprovalSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApprovalScheduleRepository extends JpaRepository<ApprovalSchedule, String> {

    ApprovalSchedule findBySchCode(String schCode);

}
