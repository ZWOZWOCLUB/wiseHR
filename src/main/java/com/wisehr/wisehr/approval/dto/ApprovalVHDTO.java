package com.wisehr.wisehr.approval.dto;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class ApprovalVHDTO {
    private Long vhiCode;
    private Long vhiSpend;
    private ApprovalCompleteDTO appCode;
    private ApprovalMemberDTO approvalMember;
}
