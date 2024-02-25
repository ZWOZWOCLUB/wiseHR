package com.wisehr.wisehr.approval.repository;

import com.wisehr.wisehr.approval.entity.ApproverProxy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApproverProxyRepository extends JpaRepository<ApproverProxy, Long> {
    ApproverProxy findFirstByRoleMemberMemCodeOrderByProEndDateDesc(Long memCode);

    List<ApproverProxy> findByProMemberMemCode(long l);

}
