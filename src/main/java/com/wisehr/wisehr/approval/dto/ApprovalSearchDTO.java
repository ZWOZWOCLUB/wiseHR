package com.wisehr.wisehr.approval.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class ApprovalSearchDTO {
    private String memCode;
    private String approvalName;
    private String approvalStart;
    private String approvalStatus;
    private String approvalType;
}
