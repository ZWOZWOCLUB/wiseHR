package com.wisehr.wisehr.schedule.dto;

import com.wisehr.wisehr.schedule.entity.Schedule;
import com.wisehr.wisehr.schedule.entity.SchedulePatternDay;
import com.wisehr.wisehr.setting.dto.SettingMemberDTO;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ScheduleAllSelectDTO {

    private String searchValue;
    private List<Schedule> scheduleList;
    private List<SettingMemberDTO> memberDTOList;
    private List<ScheduleAllowanceDTO> allowanceDTOList;
    private List<ScheduleWorkPatternDTO> workPatternDTOList;
    private List<SchedulePatternDayDTO> patternDayDTOList;
    private List<ScheduleWeekDayDTO> weekDayDTOList;
    private List<ScheduleEtcPatternDTO> etcPatternDTOList;
}
