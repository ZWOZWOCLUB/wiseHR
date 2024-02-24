package com.wisehr.wisehr.schedule.dto;

import com.wisehr.wisehr.schedule.entity.ScheduleMember;
import com.wisehr.wisehr.setting.dto.SettingMemDepPosDTO;
import com.wisehr.wisehr.setting.dto.SettingMemberDTO;
import com.wisehr.wisehr.setting.entity.SettingMember;
import lombok.*;

import java.util.List;

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
    public SettingMemDepPosDTO member;
}
