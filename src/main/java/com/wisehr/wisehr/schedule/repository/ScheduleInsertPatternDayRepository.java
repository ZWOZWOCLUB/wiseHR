package com.wisehr.wisehr.schedule.repository;

import com.wisehr.wisehr.schedule.entity.ScheduleInsertPatternDay;
import com.wisehr.wisehr.schedule.entity.ScheduleInsertPatternDayID;
import com.wisehr.wisehr.schedule.entity.SchedulePatternDay;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleInsertPatternDayRepository extends JpaRepository<ScheduleInsertPatternDay, ScheduleInsertPatternDayID> {
    ScheduleInsertPatternDay findByDayCodeAndWokCode(int dayCode, int wokCode);

    List<ScheduleInsertPatternDay> findByWokCode(int wokCode);

    void deleteByWokCode(int wokCode);
}
