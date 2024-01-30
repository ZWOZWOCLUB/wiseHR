package com.wisehr.wisehr.payment.dto;


import lombok.*;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class PaymentAttachmentDTO {
    private Long payAtcCode;
    private String payAtcName;
    private String payAtcDeleteStatus;
    private String payAtcPath;
    private PaymentDTO payment;
}
