package com.wisehr.wisehr.schedule.entity;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SchedulePatternDayID implements Serializable {

    private int dayCode;
    private int wokCode;
}
