package com.wisehr.wisehr.approval.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    private String appDate;
    private String appComment;
    private ApprovalDTO approval;
    private ApprovalMemberDTO approvalMember;
    private ApprovalPerAlarmDTO perArm;
}
