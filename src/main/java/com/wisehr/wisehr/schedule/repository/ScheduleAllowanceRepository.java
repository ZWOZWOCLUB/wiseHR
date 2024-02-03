package com.wisehr.wisehr.schedule.repository;

import com.wisehr.wisehr.schedule.entity.ScheduleAllowance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Map;

public interface ScheduleAllowanceRepository extends JpaRepository<ScheduleAllowance, Integer> {
    ScheduleAllowance findByMemCodeAndSchCode(int memCode, String schCode);

    List<ScheduleAllowance> findByMemCode(int memCode);
}
