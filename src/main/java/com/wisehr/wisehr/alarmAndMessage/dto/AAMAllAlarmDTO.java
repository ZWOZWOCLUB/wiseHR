package com.wisehr.wisehr.alarmAndMessage.dto;

import lombok.*;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AAMAllAlarmDTO {
    private int allArmCode;
    private Date allArmDate;
    private String allArmCheck;
    private String notCode;
    private int memCode;
}
