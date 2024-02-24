package com.wisehr.wisehr.approval.dto;

import lombok.*;

import java.sql.Date;
import java.util.List;

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
    private List<ReferencerDTO> refMember;
}
