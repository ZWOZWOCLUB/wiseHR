package com.wisehr.wisehr.schedule.dto;

import com.wisehr.wisehr.setting.dto.SettingMemberDTO;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ScheduleAllSelectDTO {
    private String Date;
    private List<SettingMemberDTO> memberDTOList;
    private List<ScheduleAllowanceDTO> allowanceDTOList;
    private List<ScheduleDTO> scheduleDTOList;
    private List<ScheduleWorkPatternDTO> workPatternDTOList;
    private List<ScheduleWorkPatternDTO> patternDTOList;
    private List<ScheduleWeekDayDTO> weekDayDTOList;
    private List<ScheduleEtcPatternDTO> etcPatternDTOList;
}
