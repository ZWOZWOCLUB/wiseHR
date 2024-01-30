package com.wisehr.wisehr.schedule.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.sql.Date;

@Entity
@Table(name = "per_alarm")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SchedulePerAlarm {

    @Id
    @Column(name = "per_arm_code")
    private int perArmCode;
    @Column(name = "per_arm_date_time")
    private Date perArmDateTime;
    @Column(name = "per_arm_check_status")
    private String perArmCheckStatus;
    @Column(name = "mem_code")
    private int memCode;
}
