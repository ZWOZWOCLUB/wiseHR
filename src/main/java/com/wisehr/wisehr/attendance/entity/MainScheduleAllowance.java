package com.wisehr.wisehr.attendance.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "schedule_allowance")
public class MainScheduleAllowance {

    @EmbeddedId MainScheduleAllowanceId mainScheduleAllowanceId;

    @ManyToOne
    @JoinColumn(name = "sch_code", referencedColumnName = "sch_code",insertable=false, updatable=false)
    private MainSchedule schedule;
}
