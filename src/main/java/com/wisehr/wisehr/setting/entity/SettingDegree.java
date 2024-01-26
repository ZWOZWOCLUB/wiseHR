package com.wisehr.wisehr.setting.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
@Entity
@Table(name = "degree")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SettingDegree {
    @Id
    @Column(name = "deg_code")
    private String degCode;
    @Column(name = "deg_kind")
    private String degKind;
    @Column(name = "deg_major")
    private String degMajor;
    @Column(name = "deg_name")
    private String degName;
    @Column(name = "deg_graduation")
    private String degGraduation;
    @Column(name = "deg_state")
    private String degState;
    @Column(name = "deg_admissions")
    private String degAdmissions;
    @Column(name = "mem_code")
    private int memCode;

    @Override
    public String toString() {
        return "SettingDegree{" +
                "degCode='" + degCode + '\'' +
                ", degKind='" + degKind + '\'' +
                ", degMajor='" + degMajor + '\'' +
                ", degName='" + degName + '\'' +
                ", degGraduation='" + degGraduation + '\'' +
                ", degState='" + degState + '\'' +
                ", degAdmissions='" + degAdmissions + '\'' +
                '}';
    }
}
