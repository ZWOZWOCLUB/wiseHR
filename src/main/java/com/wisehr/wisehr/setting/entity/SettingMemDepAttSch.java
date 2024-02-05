package com.wisehr.wisehr.setting.entity;

import com.wisehr.wisehr.schedule.entity.ScheduleAttendance;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "member")
public class SettingMemDepAttSch {
    @Id
    @Column(name = "mem_code", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int memCode;
    @Column(name = "mem_name", nullable = false)
    private String memName;
    @Column(name = "dep_code", nullable = true)
    private int depCode;
    @OneToOne
    @JoinColumn(name = "mem_code", insertable=false, updatable=false)
    private ScheduleAttendance attendances;
    @OneToOne
    @JoinColumn(name = "dep_code", insertable=false, updatable=false)
    private SettingDepartment department;

}
