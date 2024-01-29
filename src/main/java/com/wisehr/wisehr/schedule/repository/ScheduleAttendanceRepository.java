package com.wisehr.wisehr.schedule.repository;

import com.wisehr.wisehr.schedule.entity.ScheduleAttendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.util.List;

public interface ScheduleAttendanceRepository extends JpaRepository<ScheduleAttendance, Integer> {
    List<ScheduleAttendance> findByAttWorkDateContaining(String date);
}
