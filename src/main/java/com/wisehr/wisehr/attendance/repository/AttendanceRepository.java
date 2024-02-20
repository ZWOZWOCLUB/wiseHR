package com.wisehr.wisehr.attendance.repository;

import com.wisehr.wisehr.attendance.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    Attendance findByAttWorkDateAndAttendanceMemberMemCode(Date ediDate, Long memCode);

    Attendance findFirstByAttendanceMemberMemCodeOrderByAttWorkDateDesc(Long memCode);

    Attendance findFirstByAttendanceMemberMemCodeAndAttendanceSchedule_SchCodeOrderByAttWorkDateDesc(Long memCode, String schCode);

    Attendance findByAttendanceMemberMemCode(Long memCode);

    Attendance findByAttendanceMemberMemCodeAndAttWorkDateAndAttendanceScheduleSchCode(long l, Date searchDate, String schCode);
}
