package com.wisehr.wisehr.payment.dto;


import lombok.*;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class ApprovalDepDTO {
    private Long depCode;
    private String depName;
    private Long RefDepCode;
    private Date depBirthDate;
    private String depDeleteStatus;
}
