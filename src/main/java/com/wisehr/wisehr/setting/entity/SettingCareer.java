package com.wisehr.wisehr.setting.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
@Entity
@Table(name = "career")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SettingCareer {
    @Id
    @Column(name = "crr_code")
    private String crrCode;
    @Column(name = "crr_name")
    private String crrName;
    @Column(name = "crr_position")
    private String crrPosition;
    @Column(name = "crr_start_date")
    private String crrStartDate;
    @Column(name = "crr_end_date")
    private String crrEndDate;
    @Column(name = "crr_state")
    private String crrState;
    @Column(name = "crr_description")
    private String crrDescription;
    @Column(name = "mem_code")
    private int memCode;

    @Override
    public String toString() {
        return "SettingCareer{" +
                "crrCode='" + crrCode + '\'' +
                ", crrName='" + crrName + '\'' +
                ", crrPosition='" + crrPosition + '\'' +
                ", crrStartDate='" + crrStartDate + '\'' +
                ", crrEndDate='" + crrEndDate + '\'' +
                ", crrState='" + crrState + '\'' +
                ", crrDescription='" + crrDescription + '\'' +
                '}';
    }
}
