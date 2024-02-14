package com.wisehr.wisehr.attendance.dto;

import lombok.*;

import java.sql.Date;
import java.sql.Time;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class AttendanceDTO {
    private Long attCode;
    private Time attStartTime;
    private Time attEndTime;
    private String attStatus;
    private Date attWorkDate;
    private AttendanceMemberDTO attendanceMember;
    private AttendanceScheduleDTO attendanceSchedule;

}
