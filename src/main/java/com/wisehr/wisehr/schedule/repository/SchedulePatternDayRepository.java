package com.wisehr.wisehr.schedule.repository;

import com.wisehr.wisehr.schedule.entity.SchedulePatternDay;
import com.wisehr.wisehr.schedule.entity.SchedulePatternDayID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchedulePatternDayRepository extends JpaRepository<SchedulePatternDay, SchedulePatternDayID>  {
}
