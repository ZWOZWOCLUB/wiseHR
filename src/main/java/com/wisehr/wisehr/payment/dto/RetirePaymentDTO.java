package com.wisehr.wisehr.payment.dto;


import lombok.*;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RetirePaymentDTO {
    private String tirCode;
    private Date tirDate;
    private String tirContents;
    private ApprovalDTO payment;
}
