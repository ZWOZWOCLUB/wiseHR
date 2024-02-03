package com.wisehr.wisehr.attendance.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.sql.Time;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Attendance {
    @Id
    @Column(name = "att_code")
    private Long attCode;
    @Column(name = "att_start_time")
    private Time attStartTime;
    @Column(name = "att_end_time")
    private Time attEndTime;
    @Column(name = "att_status")
    private String attStatus;
    @Column(name = "att_work_date")
    private Date attWorkDate;
    @OneToOne
    @JoinColumn(name = "mem_code")
    private AttendanceMember attendanceMember;
    @ManyToOne
    @JoinColumn(name = "sch_code")
    private AttendanceSchedule attendanceSchedule;
}
