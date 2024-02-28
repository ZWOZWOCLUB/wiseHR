package com.wisehr.wisehr.alarmAndMessage.repository;

import com.wisehr.wisehr.alarmAndMessage.entity.AAMApproval;
import com.wisehr.wisehr.alarmAndMessage.entity.AAMApprovalComplete;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AAMApprovalRepository extends JpaRepository<AAMApproval, String> {


    AAMApproval findByPayCode(String payCode);
}
