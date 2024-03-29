package com.wisehr.wisehr.approval.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.sql.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
@Table(name = "approval_complete")
public class ApprovalComplete {
    @Id
    @GeneratedValue(generator = "eegenerator")
    @GenericGenerator(name = "eegenerator",
    parameters = @org.hibernate.annotations.Parameter(name = "prefix", value = "app"),
    strategy = "com.wisehr.wisehr.common.MyGenerator")
    @Column(name = "app_code")
    private String appCode;
    @Column(name = "app_state")
    private String appState;
    @Column(name = "app_date")
    private String appDate;
    @Column(name = "app_comment")
    private String appComment;
    @OneToOne
    @JoinColumn(name = "pay_code")
    private Approval approval;
    @OneToOne
    @JoinColumn(name = "mem_code")
    @JsonIgnore
    private ApprovalMember approvalMember;
    @OneToOne
    @JoinColumn(name = "per_arm_code")
    @JsonIgnore
    private ApprovalPerAlarm perArm;
    @OneToMany
    @JoinColumn(name = "app_code")
    @JsonIgnore
    private List<Referencer> refMember;
}
