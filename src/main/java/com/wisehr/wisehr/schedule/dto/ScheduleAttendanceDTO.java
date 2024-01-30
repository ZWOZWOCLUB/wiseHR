package com.wisehr.wisehr.schedule.dto;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ScheduleAttendanceDTO {
    private int attCode;
    private String attStartTime;
    private String attEndTime;
    private String attStatus;
    private String attWorkDate;
    private int memCode;
    private String schCode;
}
