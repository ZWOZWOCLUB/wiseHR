package com.wisehr.wisehr.schedule.dto;

import lombok.*;

import java.sql.Date;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SchedulePerAlarmDTO {

    private int perArmCode;
    private Date perArmDateTime;
    private String perArmCheckStatus;
    private int memCode;
}
