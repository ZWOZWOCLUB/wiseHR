package com.wisehr.wisehr.approval.repository;

import com.wisehr.wisehr.schedule.entity.ScheduleMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppSchMemberRepository extends JpaRepository<ScheduleMember, Integer> {

//    List<ScheduleMember> findByDepartmentDepCode(Long depCode);

    List<ScheduleMember> findByDepCodeDepCode(int depCode);
}
