package com.wisehr.wisehr.setting.dto;

import com.wisehr.wisehr.schedule.dto.ScheduleAllowanceDTO;
import com.wisehr.wisehr.schedule.dto.ScheduleDTO;
import com.wisehr.wisehr.schedule.entity.ScheduleAttendance;
import com.wisehr.wisehr.setting.entity.SettingDepartment;
import lombok.*;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SettingMemDepAttSchDTO {
    private int memCode;
    private String memName;
    private int depCode;
    private ScheduleAttendance attendances = new ScheduleAttendance();
    private SettingDepartment department = new SettingDepartment();
    private ScheduleAllowanceDTO allowanceDTO = new ScheduleAllowanceDTO();
    private ScheduleDTO scheduleDTO = new ScheduleDTO();
}
