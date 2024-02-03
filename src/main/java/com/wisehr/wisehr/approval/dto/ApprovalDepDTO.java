package com.wisehr.wisehr.approval.dto;


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
