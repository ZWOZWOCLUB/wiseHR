package com.wisehr.wisehr.schedule.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SchedulePatternInsertDTO {
    private List<SchedulePatternDayDTO> patternDayDTO;
    private ScheduleDTO scheduleDTO;
    private ScheduleWorkPatternDTO workPatternDTO;
}
