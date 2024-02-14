package com.wisehr.wisehr.schedule.repository;

import com.wisehr.wisehr.schedule.entity.SearchCountDepCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ScheduleCountDepCodeRepository extends JpaRepository<SearchCountDepCode, Integer> {
    @Query("SELECT " +
            "m.depCode, " +
            "a.depName, " +
            "count(m.memCode) as count " +
            "from SettingDepartment a " +
            "join ScheduleMember m on a.depCode = m.depCode.depCode " +
            "group by a.depName, m.depCode ")
    List<SearchCountDepCode> findByCount();
}
