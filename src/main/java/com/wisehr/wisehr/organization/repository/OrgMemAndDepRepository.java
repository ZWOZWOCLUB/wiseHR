package com.wisehr.wisehr.organization.repository;

import com.wisehr.wisehr.organization.entity.OrgMemAndOrgDep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

//멤버 전체 조회용(페이징) 레포지토리
public interface OrgMemAndDepRepository extends JpaRepository<OrgMemAndOrgDep, Integer> {
    List<OrgMemAndOrgDep> findByorgDepartmentDepCodeAndMemRole(int depCode, String ADMIN);
}
