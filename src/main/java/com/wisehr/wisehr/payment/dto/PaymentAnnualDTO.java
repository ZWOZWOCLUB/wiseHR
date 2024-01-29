package com.wisehr.wisehr.payment.dto;


import lombok.*;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class PaymentAnnualDTO {
    private String vacCode;
    private String vacKind;
    private String vacName;
    private String vacContents;
    private Date vacStartDate;
    private Date vacEndDate;
    private PaymentDTO payment;
}
