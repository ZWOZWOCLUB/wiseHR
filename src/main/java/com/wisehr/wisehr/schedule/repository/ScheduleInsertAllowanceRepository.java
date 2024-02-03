package com.wisehr.wisehr.schedule.repository;

import com.wisehr.wisehr.schedule.entity.ScheduleInsertAllowance;
import com.wisehr.wisehr.schedule.entity.ScheduleInsertAllowanceID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleInsertAllowanceRepository extends JpaRepository <ScheduleInsertAllowance, ScheduleInsertAllowanceID> {
    ScheduleInsertAllowance findByMemCodeAndSchCode(int memCode, String schCode);
}
