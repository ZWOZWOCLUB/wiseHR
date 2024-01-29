package com.wisehr.wisehr.setting.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
@Entity
@Table(name = "certificate")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SettingCertificate {
    @Id
    @Column(name = "cer_code")
    private String cerCode;
    @Column(name = "cer_name")
    private String cerName;
    @Column(name = "cer_kind")
    private String cerKind;
    @Column(name = "cer_day")
    private String cerDay;
    @Column(name = "cer_end_date")
    private String cerEndDate;
    @Column(name = "cer_description")
    private String cerDescription;
    @Column(name = "cer_institution")
    private String cerInstitution;
    @Column(name = "mem_code")
    private int memCode;

    @Override
    public String toString() {
        return "SettingCertificate{" +
                "cerCode='" + cerCode + '\'' +
                ", cerName='" + cerName + '\'' +
                ", cerKind='" + cerKind + '\'' +
                ", cerDay='" + cerDay + '\'' +
                ", cerEndDate='" + cerEndDate + '\'' +
                ", cerDescription='" + cerDescription + '\'' +
                ", cerInstitution='" + cerInstitution + '\'' +
                '}';
    }
}
