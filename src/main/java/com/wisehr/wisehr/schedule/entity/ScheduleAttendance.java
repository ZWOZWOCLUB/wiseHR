package com.wisehr.wisehr.schedule.entity;

import com.wisehr.wisehr.setting.entity.SettingAllowance;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "attendance")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ScheduleAttendance {

    @Id
    @Column(name = "att_code")
    private int attCode;
    @Column(name = "att_start_time")
    private String attStartTime;
    @Column(name = "att_end_time")
    private String attEndTime;
    @Column(name = "att_status")
    private String attStatus;
    @Column(name = "att_work_date")
    private String attWorkDate;
    @Column(name = "mem_code")
    private int memCode;
    @Column(name = "sch_code")
    private String schCode;
    @OneToOne
    @JoinColumns({
            @JoinColumn(name = "sch_code", insertable=false, updatable=false),
            @JoinColumn(name = "mem_code", insertable=false, updatable=false)
    })
    private SettingAllowance allowance;

}
