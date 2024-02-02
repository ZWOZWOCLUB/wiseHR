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
@Table(name = "schedule")
public class ScheduleAllSelect {
    @Id
    @Column(name = "sch_code")
    private String schCode;
    @Column(name = "sch_type")
    private String schType;
    @Column(name = "sch_start_date")
    private String schStartDate;
    @Column(name = "sch_end_date")
    private String schEndDate;
    @Column(name = "sch_color")
    private String schColor;
    @Column(name = "sch_delete_status")
    private String schDeleteStatus;
    @Column(name = "wok_code", insertable=false, updatable=false)
    private Integer wokCode;
    @OneToOne
    @JoinColumn(name = "wok_code")
    private ScheduleWorkPattern patternList;
    @OneToMany
    @JoinColumn(name = "wok_code")
    private List<SchedulePatternDay> patternDayList;
    @OneToMany
    @JoinColumn(name = "mem_code")
    private List<SettingMember> memberList;
    @OneToMany
    @JoinColumn(name = "sch_code")
    private List<ScheduleAllowance> allowanceList;
    @OneToMany
    @JoinColumn(name = "mem_code")
    private List<ScheduleEtcPattern> etcPatternList;

}
