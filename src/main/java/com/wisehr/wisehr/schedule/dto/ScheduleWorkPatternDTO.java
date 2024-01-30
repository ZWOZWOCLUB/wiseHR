package com.wisehr.wisehr.schedule.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ScheduleWorkPatternDTO {
    private int wokCode;
    private String wokStartTime;
    private String wokRestTime;
    private String wokEndTime;
    private String wokDeleteState;
}
