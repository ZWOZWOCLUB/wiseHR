package com.wisehr.wisehr.attendance.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "attendance")
public class MainAttendance {

    @Id
    @Column(name = "att_code")
    private Long attCode;
    @Column(name = "att_start_time")
    private LocalTime attStartTime;
    @Column(name = "att_end_time")
    private LocalTime attEndTime;
    @Column(name = "att_status")
    private String attStatus;
    @Column(name = "att_work_date")
    private LocalDate attWorkDate;

}
