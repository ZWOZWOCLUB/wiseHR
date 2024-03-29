package com.wisehr.wisehr.alarmAndMessage.dto;

import lombok.*;

import java.sql.Date;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AAMPerAlarmDTO {

    private int perArmCode;
    private Date perArmDateTime;
    private String perArmCheckStatus;
    private int memCode;
}
