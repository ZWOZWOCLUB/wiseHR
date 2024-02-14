package com.wisehr.wisehr.attendance.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class AttendanceScheduleDTO {
    private String schCode;
    private String schType;
    private String schStartDate;
    private String schEndDate;
    private String schColor;
    private String schDeleteStatus;
    private AttendanceWorkPatternDTO schWorkPattern;
}
