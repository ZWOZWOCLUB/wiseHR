package com.wisehr.wisehr.payment.dto;


import lombok.*;
import lombok.extern.slf4j.Slf4j;

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
