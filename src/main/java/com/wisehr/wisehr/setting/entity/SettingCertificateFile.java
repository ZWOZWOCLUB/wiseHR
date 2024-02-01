package com.wisehr.wisehr.setting.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@Getter
@ToString
@Entity
@Table(name = "cer_attached_file")
public class SettingCertificateFile {
    @Id
    @Column(name = "cer_atc_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cerAtcCode;
    @Column(name = "cer_atc_name")
    private String cerAtcName;
    @Column(name = "cer_atc_regist_date")
    private String cerAtcRegistDate;
    @Column(name = "cer_atc_delete_status")
    private String cerAtcDeleteStatus;
    @Column(name = "cer_atc_path")
    private String cerAtcPath;
    @Column(name = "cer_atc_convert_name")
    private String cerAtcConvertName;
    @Column(name = "cer_atc_extends")
    private String cerAtcExtends;
    @Column(name = "cer_code")
    private String cerCode;

    public SettingCertificateFile() {
    }



        public SettingCertificateFile cerAtcCode(int cerAtcCode) {
            this.cerAtcCode = cerAtcCode;
            return this;
        }

        public SettingCertificateFile cerAtcName(String cerAtcName) {
            this.cerAtcName = cerAtcName;
            return this;
        }

        public SettingCertificateFile cerAtcRegistDate(String cerAtcRegistDate) {
            this.cerAtcRegistDate = cerAtcRegistDate;
            return this;
        }

        public SettingCertificateFile cerAtcDeleteStatus(String cerAtcDeleteStatus) {
            this.cerAtcDeleteStatus = cerAtcDeleteStatus;
            return this;
        }

        public SettingCertificateFile cerAtcPath(String cerAtcPath) {
            this.cerAtcPath = cerAtcPath;
            return this;
        }

        public SettingCertificateFile cerAtcConvertName(String cerAtcConvertName) {
            this.cerAtcConvertName = cerAtcConvertName;
            return this;
        }

        public SettingCertificateFile cerAtcExtends(String cerAtcExtends) {
            this.cerAtcExtends = cerAtcExtends;
            return this;
        }

        public SettingCertificateFile cerCode(String cerCode) {
            this.cerCode = cerCode;
            return this;
        }

        public SettingCertificateFile build() {
            return new SettingCertificateFile(cerAtcCode, cerAtcName, cerAtcRegistDate, cerAtcDeleteStatus, cerAtcPath, cerAtcConvertName, cerAtcExtends, cerCode);
        }

}
