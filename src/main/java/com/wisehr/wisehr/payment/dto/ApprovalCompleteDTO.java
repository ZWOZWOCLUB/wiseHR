package com.wisehr.wisehr.payment.dto;

import lombok.*;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class ApprovalCompleteDTO {
    private String appCode;
    private String appState;
    private Date appDate;
    private String appComment;
    private ApprovalDTO payment;
    private ApprovalMemberDTO paymentMember;
    private ApprovalPerAlarmDTO perArm;
}
