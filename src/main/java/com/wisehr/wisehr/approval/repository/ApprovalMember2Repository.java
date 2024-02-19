package com.wisehr.wisehr.approval.repository;

import com.wisehr.wisehr.approval.entity.ApprovalMember;
import com.wisehr.wisehr.approval.entity.ApprovalMember2;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApprovalMember2Repository extends JpaRepository<ApprovalMember2, Long> {
    List<ApprovalMember2> findByDepartmentDepCode(Long depCode);

}
