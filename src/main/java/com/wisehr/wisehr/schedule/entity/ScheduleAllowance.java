package com.wisehr.wisehr.schedule.entity;

import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name = "schedule_allowance")
//@IdClass(ScheduleAllowanceID.class)
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ScheduleAllowance {
    @EmbeddedId
    private ScheduleAllowanceID allowanceID;

    @OneToOne
    @JoinColumn(name = "mem_code", insertable = false, updatable = false)
    private ScheduleMember member;

    public ScheduleAllowance() {
    }



}
