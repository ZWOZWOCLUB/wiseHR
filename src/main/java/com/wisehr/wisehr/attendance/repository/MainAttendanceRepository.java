package com.wisehr.wisehr.attendance.repository;

import com.wisehr.wisehr.attendance.entity.MainAttendance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MainAttendanceRepository extends JpaRepository<MainAttendance, Long> {
}
