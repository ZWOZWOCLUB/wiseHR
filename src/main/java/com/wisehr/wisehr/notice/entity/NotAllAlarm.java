package com.wisehr.wisehr.notice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Table(name = "all_alarm")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class NotAllAlarm {
    @Id
    @Column(name = "all_arm_code")
    private int allArmCode;

    @Column(name = "all_arm_date")
    private LocalDateTime allArmDate;

    @Column(name = "all_arm_check")
    private String allArmCheck;

    @Column(name = "not_code")
    private String notCode;


    @Column(name = "mem_code")
    private int memCode;
}
