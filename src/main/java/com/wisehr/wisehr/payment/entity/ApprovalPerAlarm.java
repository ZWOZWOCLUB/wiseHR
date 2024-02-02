package com.wisehr.wisehr.payment.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Table(name = "per_alarm")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ApprovalPerAlarm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "per_arm_code")
    private Long perArmCode;
    @Column(name = "per_arm_date_time")
    private LocalDateTime perArmDateTime;
    @Column(name = "per_arm_check_status")
    private String perArmCheckStatus;
    @OneToOne
    @JoinColumn(name="mem_code")
    @JsonIgnore
    private ReceiveAramMember receiveAramMember;
}
