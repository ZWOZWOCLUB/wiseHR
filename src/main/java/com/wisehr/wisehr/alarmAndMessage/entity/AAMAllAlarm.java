package com.wisehr.wisehr.alarmAndMessage.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.sql.Date;


@Entity
@Table(name = "all_alarm")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AAMAllAlarm {
    @Id
    @Column(name = "all_arm_code")
    private int allArmCode;
    @Column(name = "all_arm_date")
    private Date allArmDate;
    @Column(name = "all_arm_check")
    private String allArmCheck;
    @Column(name = "not_code")
    private String notCode;
    @Column(name = "mem_code")
    private int memCode;
}
