package com.wisehr.wisehr.payment.dto;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ReqDocumentPaymentDTO {
    private String reqCode;
    private String reqKind;
    private String reqUse;
    private PaymentDTO payment;
}
