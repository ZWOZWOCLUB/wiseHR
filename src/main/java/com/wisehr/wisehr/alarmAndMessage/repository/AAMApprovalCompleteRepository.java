package com.wisehr.wisehr.alarmAndMessage.repository;

import com.wisehr.wisehr.alarmAndMessage.entity.AAMAllAlarm;
import com.wisehr.wisehr.alarmAndMessage.entity.AAMApprovalComplete;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AAMApprovalCompleteRepository extends JpaRepository<AAMApprovalComplete, String> {

    AAMApprovalComplete findByPerArmCode(int perArmCode);
}
