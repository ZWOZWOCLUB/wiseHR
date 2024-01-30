package com.wisehr.wisehr.organization.repository;

import com.wisehr.wisehr.organization.entity.OrgDepartmentAndOrgMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrgDepAndMemRepository extends JpaRepository<OrgDepartmentAndOrgMember, Integer> {
}
