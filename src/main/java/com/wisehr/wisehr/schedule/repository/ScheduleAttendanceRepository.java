package com.wisehr.wisehr.schedule.repository;

import com.wisehr.wisehr.mypage.entity.Attendance;
import com.wisehr.wisehr.schedule.entity.ScheduleAttendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.util.List;

public interface ScheduleAttendanceRepository extends JpaRepository<Attendance, Integer> {
    List<ScheduleAttendance> findByAttWorkDate(Date date);
}
