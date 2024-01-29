package com.wisehr.wisehr.payment.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class ReferencerDTO {
    private PaymentMemberDTO memCode;
    private ApprovalPaymentDTO appCode;
}
