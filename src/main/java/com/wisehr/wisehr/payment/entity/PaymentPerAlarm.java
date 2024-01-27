package com.wisehr.wisehr.payment.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
@Entity
@Table(name = "per_alarm")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PaymentPerAlarm {
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
