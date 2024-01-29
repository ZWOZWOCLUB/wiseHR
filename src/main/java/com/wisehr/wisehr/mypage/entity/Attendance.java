package com.wisehr.wisehr.mypage.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Time;
import java.util.Date;

@Entity
@Table(name = "attendance")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "att_code")
    private int attCode;
    @Column(name = "att_start_time")
    private Time attStartTime;
    @Column(name = "att_end_time")
    private Time attEndTime;
    @Column(name = "att_status")
    private String attStatus;
    @Column(name = "att_work_date")
    private java.sql.Date attWorkDate;
    @Column(name = "mem_code")
    private int memCode;
    @Column(name = "sch_code")
    private String schCode;
}
