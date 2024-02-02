package com.wisehr.wisehr.schedule.entity;

import com.wisehr.wisehr.schedule.dto.*;
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
@Entity
public class ScheduleAllSelect {
    @Id
    private String Date;
//    private List<SettingMember> memberList;
//    private List<ScheduleAllowance> allowanceList;
//    private List<Schedule> scheduleList;
//    private List<ScheduleWorkPattern> patternList;
//    @OneToMany
//    @JoinColumn(name = "day_code")
//    private List<ScheduleWeekDay> weekDayList;
//    @OneToMany
//    @JoinColumn(name = "mem_code")
//    private List<ScheduleEtcPattern> etcPatternList;
}
