package com.wisehr.wisehr.schedule.repository;

import com.wisehr.wisehr.schedule.entity.ScheduleInsertPatternDay;
import com.wisehr.wisehr.schedule.entity.ScheduleInsertPatternDayID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleInsertPatternDayRepository extends JpaRepository<ScheduleInsertPatternDay, ScheduleInsertPatternDayID> {
    ScheduleInsertPatternDay findByDayCodeAndWokCode(int dayCode, int wokCode);
}
