package com.wisehr.wisehr.organization.repository;

import com.wisehr.wisehr.organization.dto.TreeMemDTO;
import com.wisehr.wisehr.organization.entity.TreeMem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrgTreeMemRepository extends JpaRepository<TreeMem, Integer> {


    @Query("select new com.wisehr.wisehr.organization.dto.TreeMemDTO(m.memCode, m.memName, p.posName) from TreeMem m join TreePos p on m.treePos = p.posCode where m.treeDep.depCode = :depCode and m.memStatus = 'N'")
    List<TreeMemDTO> findMembersByDepartment(@Param("depCode") Integer depCode);

    @Query("select new com.wisehr.wisehr.organization.dto.TreeMemDTO(m.memCode, m.memName, p.posName) " +
            "from TreeMem m " +
            "join TreePos p on m.treePos = p.posCode " +
            "left join ScheduleInsertAllowance C on m.memCode = C.memCode " +
            "where m.treeDep.depCode = :depCode and m.memStatus = 'N'" +
            "AND C.memCode is null ")
    List<TreeMemDTO> findMembersByDepartmentNotContainSchedule(Integer depCode);
}
