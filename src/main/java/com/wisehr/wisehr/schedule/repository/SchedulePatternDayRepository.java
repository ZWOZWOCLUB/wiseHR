package com.wisehr.wisehr.schedule.repository;

import com.wisehr.wisehr.schedule.dto.SchedulePatternDayDTO;
import com.wisehr.wisehr.schedule.entity.SchedulePatternDay;
import com.wisehr.wisehr.schedule.entity.SchedulePatternDayID;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SchedulePatternDayRepository extends JpaRepository<SchedulePatternDay, SchedulePatternDayID>  {
    SchedulePatternDay findByPatternDayID(SchedulePatternDayID schedulePatternDayID);
}
