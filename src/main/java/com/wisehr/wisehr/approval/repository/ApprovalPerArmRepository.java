package com.wisehr.wisehr.approval.repository;

import com.wisehr.wisehr.approval.entity.ApprovalPerAlarm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApprovalPerArmRepository extends JpaRepository<ApprovalPerAlarm, Long> {
}
