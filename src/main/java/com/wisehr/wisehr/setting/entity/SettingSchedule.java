package com.wisehr.wisehr.setting.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "schedule")
public class SettingSchedule {
    @Id
    @Column(name = "sch_code")
    private String schCode;
    @Column(name = "sch_type")
    private String schType;
    @Column(name = "sch_color")
    private String schColor;
    @Column(name = "sch_delete_status")
    private String schDeleteStatus;

    public SettingSchedule() {
    }


    public SettingSchedule schCode(String schCode) {
        this.schCode = schCode;
        return this;
    }

    public SettingSchedule schType(String schType) {
        this.schType = schType;
        return this;
    }


    public SettingSchedule schColor(String schColor) {
        this.schColor = schColor;
        return this;
    }


    public SettingSchedule build() {
        return new SettingSchedule(schCode, schType, schColor, schDeleteStatus);

    }
}
