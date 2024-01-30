package com.wisehr.wisehr.payment.dto;

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
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+9")
    private Date appDate;
    private String appComment;
    private ApprovalDTO approval;
    private ApprovalMemberDTO approvalMember;
    private ApprovalPerAlarmDTO perArm;
}
