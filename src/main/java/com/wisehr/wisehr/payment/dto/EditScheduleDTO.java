package com.wisehr.wisehr.payment.dto;


import lombok.*;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class EditScheduleDTO {
    private String eshCode;
    private String eshName;
    private Date eshStartDate;
    private Date eshEndDate;
    private String eshContents;
    private PaymentDTO payCode;
}
