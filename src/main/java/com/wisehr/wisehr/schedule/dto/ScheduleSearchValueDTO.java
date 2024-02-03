package com.wisehr.wisehr.schedule.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ScheduleSearchValueDTO {
    private int memCode;
    private String memName;
    private int depCode;
    private String depName;
    private String chooseDate;
    private String yearMonth;

}
