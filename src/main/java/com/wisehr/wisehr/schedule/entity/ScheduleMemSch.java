package com.wisehr.wisehr.schedule.entity;

import com.wisehr.wisehr.setting.entity.SettingDepartment;
import com.wisehr.wisehr.setting.entity.SettingPosition;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name ="member")
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class ScheduleMemSch {
    @Id
    @Column(name = "mem_code", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int memCode;
    @Column(name = "mem_name", nullable = false)
    private String memName;
    @Column(name = "mem_status", nullable = false)
    private String memStatus;
    @OneToOne
    @JoinColumn(name = "dep_code")
    private SettingDepartment deplist;
    @OneToOne
    @JoinColumn(name = "pos_code")
    private SettingPosition poslist;

    @OneToMany
    @JoinColumn(name = "mem_code")
    private List<ScheduleAllowanceMem> allowanceID;

    @Override
    public String toString() {
        return "ScheduleMemSch{" +
                "memName='" + memName + '\'' +
                ", memStatus='" + memStatus + '\'' +
                ", deplist=" + deplist +
                ", poslist=" + poslist +
                '}';
    }
}
