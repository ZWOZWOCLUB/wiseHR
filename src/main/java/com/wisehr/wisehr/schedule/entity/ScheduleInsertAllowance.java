package com.wisehr.wisehr.schedule.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "schedule_allowance")
@IdClass(ScheduleAllowanceID.class)
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ScheduleInsertAllowance {
    @Id
    @Column(name = "mem_code")
    private int memCode;
    @Id
    @Column(name = "sch_code")
    private String schCode;



    public ScheduleInsertAllowance() {
    }


}
