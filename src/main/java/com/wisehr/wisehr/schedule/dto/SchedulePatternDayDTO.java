package com.wisehr.wisehr.schedule.dto;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SchedulePatternDayDTO {
    private int dayCode;
    private int wokCode;
    private int changeDayCode;
}
