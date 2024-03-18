package com.wisehr.wisehr.setting.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@Getter
@ToString
@Entity
@Table(name = "deg_attached_file")
public class SettingDegreeFile {
    @Id
    @Column(name = "deg_atc_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int degAtcCode;
    @Column(name = "deg_atc_name")
    private String degAtcName;
    @Column(name = "deg_atc_regist_date")
    private String degAtcRegistDate;
    @Column(name = "deg_atc_delete_status")
    private String degAtcDeleteStatus;
    @Column(name = "deg_atc_path")
    private String degAtcPath;
    @Column(name = "deg_atc_extends")
    private String degAtcExtends;
    @Column(name = "deg_atc_convert_name")
    private String degAtcConvertName;
    @Column(name = "deg_code")
    private String degCode;
    @OneToOne
    @JoinColumn(name = "deg_code", insertable = false, updatable = false)
    private SettingDegree degree;

    public SettingDegreeFile() {
    }

    public SettingDegreeFile(int degAtcCode, String degAtcName, String degAtcRegistDate, String degAtcDeleteStatus, String degAtcPath, String degAtcExtends, String degAtcConvertName, String degCode) {
        }
        public SettingDegreeFile degAtcCode(int degAtcCode) {
            this.degAtcCode = degAtcCode;
            return this;
        }

        public SettingDegreeFile degAtcName(String degAtcName) {
            this.degAtcName = degAtcName;
            return this;
        }

        public SettingDegreeFile degAtcRegistDate(String degAtcRegistDate) {
            this.degAtcRegistDate = degAtcRegistDate;
            return this;
        }

        public SettingDegreeFile degAtcDeleteStatus(String degAtcDeleteStatus) {
            this.degAtcDeleteStatus = degAtcDeleteStatus;
            return this;
        }

        public SettingDegreeFile degAtcPath(String degAtcPath) {
            this.degAtcPath = degAtcPath;
            return this;
        }

        public SettingDegreeFile degAtcExtends(String degAtcExtends) {
            this.degAtcExtends = degAtcExtends;
            return this;
        }

        public SettingDegreeFile degAtcConvertName(String degAtcConvertName) {
            this.degAtcConvertName = degAtcConvertName;
            return this;
        }

        public SettingDegreeFile degCode(String degCode) {
            this.degCode = degCode;
            return this;
        }

        public SettingDegreeFile build() {
            return new SettingDegreeFile(degAtcCode, degAtcName, degAtcRegistDate, degAtcDeleteStatus, degAtcPath, degAtcExtends, degAtcConvertName, degCode);
        }

}
