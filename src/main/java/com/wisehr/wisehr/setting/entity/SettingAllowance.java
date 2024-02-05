package com.wisehr.wisehr.setting.entity;

import com.wisehr.wisehr.schedule.entity.ScheduleMember;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Entity
@Table(name = "schedule_allowance")
//@IdClass(ScheduleAllowanceID.class)
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SettingAllowance {
    @EmbeddedId
    private SettingAllowanceID allowanceID;

    @OneToOne
    @JoinColumn(name = "sch_code", insertable = false, updatable = false)
    private SettingSchedule schedule;





    public SettingAllowance() {
    }


}
