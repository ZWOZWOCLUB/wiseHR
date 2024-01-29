package com.wisehr.wisehr.payment.dto;

import lombok.*;

import java.sql.Date;
import java.sql.Time;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class EditCommuteDTO {
    private String ediCode;
    private String ediKind;
    private String ediName;
    private String ediContents;
    private Date ediDate;
    private Time ediTime;
    private PaymentDTO payCode;
}
