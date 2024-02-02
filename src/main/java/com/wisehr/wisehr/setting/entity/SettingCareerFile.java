package com.wisehr.wisehr.setting.entity;

import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name = "crr_attached_file")
@AllArgsConstructor
@Getter
@ToString
public class SettingCareerFile {
    @Id
    @Column(name = "crr_atc_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int crrAtcCode;
    @Column(name = "crr_atc_name")
    private String crrAtcName;
    @Column(name = "crr_atc_regist_date")
    private String crrAtcRegistDate;
    @Column(name = "crr_atc_delete_status")
    private String crrAtcDeleteStatus;
    @Column(name = "crr_atc_path")
    private String crrAtcPath;
    @Column(name = "crr_atc_extends")
    private String crrAtcExtends;
    @Column(name = "crr_atc_convert_name")
    private String crrAtcConvertName;
    @Column(name = "crr_code")
    private String crrCode;

    public SettingCareerFile() {
    }


        public SettingCareerFile crrAtcCode(int crrAtcCode) {
            this.crrAtcCode = crrAtcCode;
            return this;
        }

        public SettingCareerFile crrAtcName(String crrAtcName) {
            this.crrAtcName = crrAtcName;
            return this;
        }

        public SettingCareerFile crrAtcRegistDate(String crrAtcRegistDate) {
            this.crrAtcRegistDate = crrAtcRegistDate;
            return this;
        }

        public SettingCareerFile crrAtcDeleteStatus(String crrAtcDeleteStatus) {
            this.crrAtcDeleteStatus = crrAtcDeleteStatus;
            return this;
        }

        public SettingCareerFile crrAtcPath(String crrAtcPath) {
            this.crrAtcPath = crrAtcPath;
            return this;
        }

        public SettingCareerFile crrAtcExtends(String crrAtcExtends) {
            this.crrAtcExtends = crrAtcExtends;
            return this;
        }

        public SettingCareerFile crrAtcConvertName(String crrAtcConvertName) {
            this.crrAtcConvertName = crrAtcConvertName;
            return this;
        }

        public SettingCareerFile crrCode(String crrCode) {
            this.crrCode = crrCode;
            return this;
        }


        public SettingCareerFile build() {
            return new SettingCareerFile(crrAtcCode, crrAtcName, crrAtcRegistDate, crrAtcDeleteStatus, crrAtcPath, crrAtcExtends, crrAtcConvertName, crrCode);
        }



}
