package com.wisehr.wisehr.payment.dto;

import lombok.*;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ApprovalPerAlarmDTO {
    private int perArmCode;
    private Date perArmDateTime;
    private String perArmCheckStatus;
    private int memCode;
}
