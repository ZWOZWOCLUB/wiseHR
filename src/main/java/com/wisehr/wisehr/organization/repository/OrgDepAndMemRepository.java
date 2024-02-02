package com.wisehr.wisehr.organization.repository;

import com.wisehr.wisehr.organization.entity.OrgDepartmentAndOrgMember;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrgDepAndMemRepository extends JpaRepository<OrgDepartmentAndOrgMember, Integer> {

    //N+1 문제를 해결하기 위해 fetch 조인 사용
    @Query("select od from OrgDepartmentAndOrgMember od Left join Fetch od.memberList m left join fetch m.orgPosition where od.depDeleteStatus = 'N'")
    List<OrgDepartmentAndOrgMember> findAllMemOfDep();

//    Page<OrgDepartmentAndOrgMember> findByOrgDepartmentAndOrgMemberOrderable(String , Pageable );
}
