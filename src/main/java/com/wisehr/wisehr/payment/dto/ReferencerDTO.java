package com.wisehr.wisehr.payment.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class ReferencerDTO {
    private ApprovalMemberDTO memCode;
    private ApprovalCompleteDTO appCode;
}
