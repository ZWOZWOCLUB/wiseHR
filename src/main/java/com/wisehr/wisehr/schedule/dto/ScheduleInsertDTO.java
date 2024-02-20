package com.wisehr.wisehr.schedule.dto;

import com.wisehr.wisehr.common.DateToStringConverter;
import jakarta.persistence.Convert;
import lombok.*;

import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ScheduleInsertDTO {
    private List<SchedulePatternDayDTO> patternDayDTO;
    private ScheduleDTO scheduleDTO;
    private int wokCode;
    private String schCode;
    private String schType;
    @Convert(converter= DateToStringConverter.class)
    private String schStartDate;
    @Convert(converter= DateToStringConverter.class)
    private String schEndDate;
    private String schColor;
    private String schDeleteStatus;
    private List<String> dayCode;

}
