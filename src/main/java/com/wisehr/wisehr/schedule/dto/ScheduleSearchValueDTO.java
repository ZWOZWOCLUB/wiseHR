package com.wisehr.wisehr.schedule.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ScheduleSearchValueDTO {
    private List<String > memberCode;
    private int memCode;
    private String memName;
    private int depCode;
    private String depName;
    private String chooseDate;
    private String yearMonth;
    private String notContain;
}
