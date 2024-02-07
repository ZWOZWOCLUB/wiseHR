package com.wisehr.wisehr.approval.repository;

import com.wisehr.wisehr.approval.entity.ApprovalMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApprovalMemberRepository extends JpaRepository<ApprovalMember, Long> {
    ApprovalMember findByMemCode(Long memCode);

    List<ApprovalMember> findByDepartmentDepCode(Long depCode);

    List<ApprovalMember> findByMemPassword(String number);

    List<ApprovalMember> findByMemBirthLike(String s);

}
