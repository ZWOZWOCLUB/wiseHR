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
    private String schCode;
    private String schType;
    private LocalDate schStartDate;
    private LocalDate schEndDate;
    private String schColor;
    private String schDeleteStatus;
    private ApprovalWorkPatternDTO workPattern;
}
