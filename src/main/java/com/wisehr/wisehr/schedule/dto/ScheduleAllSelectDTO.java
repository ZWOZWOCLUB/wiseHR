package com.wisehr.wisehr.schedule.dto;

import com.wisehr.wisehr.schedule.entity.*;
import com.wisehr.wisehr.setting.dto.SettingMemberDTO;
import com.wisehr.wisehr.setting.entity.SettingMember;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ScheduleAllSelectDTO {

    private String schCode;
    private String schType;
    private String schStartDate;
    private String schEndDate;
    private String schColor;
    private String schDeleteStatus;
    private int wokCode;
    private ScheduleWorkPattern patternList;
    private List<SchedulePatternDay> patternDayList;
    private List<SettingMember> memberList;
    private List<ScheduleAllowance> allowanceList;
    private List<ScheduleEtcPattern> etcPatternList;
}
