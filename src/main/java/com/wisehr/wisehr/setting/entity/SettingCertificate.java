package com.wisehr.wisehr.setting.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "certificate")
@AllArgsConstructor
@Getter
public class SettingCertificate {
    @Id
    @GeneratedValue(generator = "eegenerator")
    @GenericGenerator(name = "eegenerator",
            parameters = @org.hibernate.annotations.Parameter(name = "prefix", value = "cer"),
            strategy = "com.wisehr.wisehr.common.MyGenerator")
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

    public SettingCertificate() {
    }



        public SettingCertificate cerCode(String cerCode) {
            this.cerCode = cerCode;
            return this;
        }

        public SettingCertificate cerName(String cerName) {
            this.cerName = cerName;
            return this;
        }

        public SettingCertificate cerKind(String cerKind) {
            this.cerKind = cerKind;
            return this;
        }

        public SettingCertificate cerDay(String cerDay) {
            this.cerDay = cerDay;
            return this;
        }

        public SettingCertificate cerEndDate(String cerEndDate) {
            this.cerEndDate = cerEndDate;
            return this;
        }

        public SettingCertificate cerDescription(String cerDescription) {
            this.cerDescription = cerDescription;
            return this;
        }

        public SettingCertificate cerInstitution(String cerInstitution) {
            this.cerInstitution = cerInstitution;
            return this;
        }

        public SettingCertificate memCode(int memCode) {
            this.memCode = memCode;
            return this;
        }

        public SettingCertificate build() {
            return new SettingCertificate(cerCode, cerName, cerKind, cerDay, cerEndDate, cerDescription, cerInstitution, memCode);
        }

}
