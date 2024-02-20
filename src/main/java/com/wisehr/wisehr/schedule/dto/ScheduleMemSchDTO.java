package com.wisehr.wisehr.schedule.dto;

import com.wisehr.wisehr.setting.dto.SettingDepartmentDTO;
import com.wisehr.wisehr.setting.dto.SettingPositionDTO;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ScheduleMemSchDTO {
    private int memCode;
    private String memName;
    private String memPhone;
    private String memEmail;
    private String memAddress;
    private String memBirth;
    private String memPassword;
    private String memHireDate;
    private String memEndDate;
    private String memStatus;
    private String memRole;
    private SettingPositionDTO posList;
    private SettingDepartmentDTO depList;
    private List<ScheduleAllowanceDTO> allowanceDTOList;
    private ScheduleDTO scheduleDTO;
}
