package com.wisehr.wisehr.attendance.dto;

import lombok.*;

import java.sql.Date;
import java.sql.Time;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Attendance2DTO {
    private Long attCode;
    private String attStartTime;
    private String attEndTime;
    private String attStatus;
    private String attWorkDate;
    private AttendanceMemberDTO attendanceMember;
    private AttendanceScheduleDTO attendanceSchedule;

}
