package com.wisehr.wisehr.schedule.repository;

import com.wisehr.wisehr.schedule.entity.ScheduleEtcPattern;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleEtcPatternRepository extends JpaRepository<ScheduleEtcPattern, Integer> {
}
