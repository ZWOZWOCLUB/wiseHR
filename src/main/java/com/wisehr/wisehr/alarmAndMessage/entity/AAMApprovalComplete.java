package com.wisehr.wisehr.alarmAndMessage.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "approval_complete")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AAMApprovalComplete {

    @Id
    @Column(name = "app_code")
    private String appCode;
    @Column(name = "mem_code")
    private int memCode;
    @Column(name = "app_state")
    private String appState;
    @Column(name = "app_date")
    private String appDate;
    @Column(name = "app_comment")
    private String appComment;
    @Column(name = "pay_code")
    private String payCode;
    @Column(name = "per_arm_code")
    private int perArmCode;

}
