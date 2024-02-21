package com.wisehr.wisehr.schedule.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "schedule_allowance")
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ScheduleAllowanceMem {
    @EmbeddedId
    private ScheduleAllowanceID allowanceID;

@ManyToOne
@JoinColumn(name = "sch_code", insertable = false, updatable = false)
private Schedule schedule;


    public ScheduleAllowanceMem() {
    }



}
