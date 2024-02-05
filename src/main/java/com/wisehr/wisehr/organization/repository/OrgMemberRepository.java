package com.wisehr.wisehr.organization.repository;

import com.wisehr.wisehr.common.Criteria;
import com.wisehr.wisehr.organization.entity.OrgMember;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrgMemberRepository extends JpaRepository<OrgMember, Integer> {


//    int countByDepCodeAndMemRole(int depCode, String 중간관리자);
}
