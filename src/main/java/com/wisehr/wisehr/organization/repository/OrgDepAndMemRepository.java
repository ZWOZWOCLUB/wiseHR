package com.wisehr.wisehr.organization.repository;

import com.wisehr.wisehr.organization.entity.OrgDepartmentAndOrgMember;
import com.wisehr.wisehr.organization.entity.OrgMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrgDepAndMemRepository extends JpaRepository<OrgDepartmentAndOrgMember, Integer> {
}
