package com.wisehr.wisehr.attendance.repository;

import com.wisehr.wisehr.attendance.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    Attendance findByAttWorkDateAndAttendanceMemberMemCode(Date ediDate, Long memCode);
}
