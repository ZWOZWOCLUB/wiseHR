package com.wisehr.wisehr.schedule.repository;

import com.wisehr.wisehr.schedule.entity.ScheduleAllowance;
import com.wisehr.wisehr.schedule.entity.ScheduleAllowanceID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleAllowanceRepository extends JpaRepository<ScheduleAllowance, ScheduleAllowanceID> {
    ScheduleAllowance findByAllowanceID(ScheduleAllowanceID scheduleAllowanceID);
}
