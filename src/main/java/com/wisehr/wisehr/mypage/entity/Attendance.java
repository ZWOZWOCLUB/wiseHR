package com.wisehr.wisehr.mypage.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

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
    @Column(name = "att_code")
    private int attCode;
    @Column(name = "att_start_time")
    private java.util.Date attStartTime;
    @Column(name = "att_end_time")
    private java.util.Date attEndTime;
    @Column(name = "att_status")
    private String attStatus;
    @Column(name = "att_work_date")
    private java.sql.Date attWorkDate;
    @Column(name = "mem_code")
    private int memCode;
    @Column(name = "sch_code")
    private String schCode;
}
