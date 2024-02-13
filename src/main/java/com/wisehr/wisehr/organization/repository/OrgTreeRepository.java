package com.wisehr.wisehr.organization.repository;

import com.wisehr.wisehr.organization.dto.TreeDepDTO;
import com.wisehr.wisehr.organization.entity.TreeDep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrgTreeRepository extends JpaRepository<TreeDep, Integer> {
    @Query("select new com.wisehr.wisehr.organization.dto.TreeDepDTO(d.depCode, d.depName) from TreeDep d where d.parentTreeDep is null")
    List<TreeDepDTO> findTopDep();
    @Query("select new com.wisehr.wisehr.organization.dto.TreeDepDTO(d.depCode, d.depName) from TreeDep d where d.parentTreeDep.depCode = :depCode and d.depDeleteStatus = 'N'")
    List<TreeDepDTO> findSubDep(Integer depCode);

}
