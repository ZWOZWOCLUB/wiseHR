package com.wisehr.wisehr.schedule.repository;

import com.wisehr.wisehr.schedule.entity.ScheduleInsertAllowance;
import com.wisehr.wisehr.schedule.entity.ScheduleInsertAllowanceID;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleInsertAllowanceRepository extends JpaRepository <ScheduleInsertAllowance, ScheduleInsertAllowanceID> {
    ScheduleInsertAllowance findByMemCodeAndSchCode(int memCode, String schCode);

    List<ScheduleInsertAllowance> findBySchCode(String schCode);

    void deleteBySchCodeAndMemCode(String schCode, int memCode);
}
