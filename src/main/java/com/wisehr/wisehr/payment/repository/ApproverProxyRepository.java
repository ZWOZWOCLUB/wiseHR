package com.wisehr.wisehr.payment.repository;

import com.wisehr.wisehr.payment.dto.ApprovalMemberDTO;
import com.wisehr.wisehr.payment.entity.ApproverProxy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApproverProxyRepository extends JpaRepository<ApproverProxy, Long> {
    ApproverProxy findFirstByRoleMemberMemCodeOrderByProEndDateDesc(Long memCode);
}
