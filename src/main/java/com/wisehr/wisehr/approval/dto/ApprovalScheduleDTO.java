package com.wisehr.wisehr.approval.dto;

import lombok.*;

import java.sql.Date;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class ApprovalScheduleDTO {
    String schCode;
    String schType;
    LocalDate schStartDate;
    LocalDate schEndDate;
    String schColor;
    String schDeleteStatus;
    ApprovalWorkPatternDTO workPattern;
}
