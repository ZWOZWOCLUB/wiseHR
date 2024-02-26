package com.wisehr.wisehr.approval.repository;

import com.wisehr.wisehr.approval.entity.ApproverProxy;
import com.wisehr.wisehr.attendance.dto.Attendance2DTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface ApproverProxyRepository extends JpaRepository<ApproverProxy, Long> {

    ApproverProxy findByProEndDateAndRoleMemberMemCode(LocalDate proEndDate, Long roleMember);
    ApproverProxy findFirstByRoleMemberMemCodeOrderByProEndDateDesc(Long memCode);

    List<ApproverProxy> findByProMemberMemCode(long l);

}
