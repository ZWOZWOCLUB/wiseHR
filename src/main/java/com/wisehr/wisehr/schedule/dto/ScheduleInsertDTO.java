package com.wisehr.wisehr.schedule.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ScheduleInsertDTO {
    private List<SchedulePatternDayDTO> patternDayDTO;
    private ScheduleDTO scheduleDTO;
}
