package com.wisehr.wisehr.setting.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@Getter
@ToString
@Entity
@Table(name = "sal_attached_file")
public class SettingSalaryFile {
    @Id
    @Column(name = "sal_atc_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int salAtcCode;
    @Column(name = "sal_atc_name")
    private String salAtcName;
    @Column(name = "sal_atc_delete_status")
    private String salAtcDeleteStatus;
    @Column(name = "sal_atc_path")
    private String salAtcPath;
    @Column(name = "sal_atc_regist_date")
    private String salAtcRegistDate;
    @Column(name = "sal_code")
    private String salCode;
    @Column(name = "sal_atc_convert_name")
    private String salAtcConvertName;

    public SettingSalaryFile() {
    }



        public SettingSalaryFile salAtcCode(int salAtcCode) {
            this.salAtcCode = salAtcCode;
            return this;
        }

        public SettingSalaryFile salAtcName(String salAtcName) {
            this.salAtcName = salAtcName;
            return this;
        }

        public SettingSalaryFile salAtcDeleteStatus(String salAtcDeleteStatus) {
            this.salAtcDeleteStatus = salAtcDeleteStatus;
            return this;
        }

        public SettingSalaryFile salAtcPath(String salAtcPath) {
            this.salAtcPath = salAtcPath;
            return this;
        }

        public SettingSalaryFile salAtcRegistDate(String salAtcRegistDate) {
            this.salAtcRegistDate = salAtcRegistDate;
            return this;
        }

        public SettingSalaryFile salCode(String salCode) {
            this.salCode = salCode;
            return this;
        }

        public SettingSalaryFile salAtcConvertName(String salAtcConvertName) {
            this.salAtcConvertName = salAtcConvertName;
            return this;
        }

        public SettingSalaryFile build() {
            return new SettingSalaryFile(salAtcCode, salAtcName, salAtcDeleteStatus, salAtcPath, salAtcRegistDate, salCode, salAtcConvertName);
        }
    }



