package com.wisehr.wisehr.schedule.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ScheduleEtcPatternDTO {
    public int etcCode;
    public int memCode;
    public String etcDate;
    public String etcKind;
}
