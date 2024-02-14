package com.wisehr.wisehr.schedule.repository;

import com.wisehr.wisehr.schedule.entity.ScheduleWorkPattern;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleWorkPatternRepository extends JpaRepository<ScheduleWorkPattern, Integer> {

}
