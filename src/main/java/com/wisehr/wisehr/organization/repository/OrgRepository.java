package com.wisehr.wisehr.organization.repository;

import com.wisehr.wisehr.organization.entity.OrgDepartment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrgRepository extends JpaRepository<OrgDepartment, Integer> {
}
