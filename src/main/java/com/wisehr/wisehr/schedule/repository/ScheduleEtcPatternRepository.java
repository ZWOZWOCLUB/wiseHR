package com.wisehr.wisehr.schedule.repository;

import com.wisehr.wisehr.schedule.entity.ScheduleEtcPattern;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ScheduleEtcPatternRepository extends JpaRepository<ScheduleEtcPattern, Integer> {

    List<ScheduleEtcPattern> findAll();

}
