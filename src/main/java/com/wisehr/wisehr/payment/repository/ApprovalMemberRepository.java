package com.wisehr.wisehr.payment.repository;

import com.wisehr.wisehr.payment.entity.ApprovalMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApprovalMemberRepository extends JpaRepository<ApprovalMember, Long> {
    ApprovalMember findByMemCode(Long memCode);

    List<ApprovalMember> findByDepartmentDepCode(Long depCode);
}
