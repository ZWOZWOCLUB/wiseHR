package com.wisehr.wisehr.schedule.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ScheduleDTO {
    private String schCode;
    private String schType;
    private String schStartDate;
    private String schEndDate;
    private String schColor;
    private String schDeleteStatus;
    private int wokCode;
}
