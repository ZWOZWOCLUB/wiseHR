package com.wisehr.wisehr.organization.repository;

import com.wisehr.wisehr.organization.entity.OrgDepartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrgRepository extends JpaRepository<OrgDepartment, Integer> {

    @Query("SELECT od FROM OrgDepartment od where (od.refDepCode Is null OR od.refDepCode = 1) and od.depDeleteStatus = 'N' order by od.depCode")
    List<OrgDepartment> findRefDepCode();

    List<OrgDepartment> findByDepDeleteStatus(String depDeleteStatus);

}
