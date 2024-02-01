package com.wisehr.wisehr.setting.entity;

import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name = "profile")
@AllArgsConstructor
@Getter
@ToString
public class SettingProfile {
    @Id
    @Column(name = "prf_code", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int prfCode;
    @Column(name = "prf_path")
    private String prfPath;
    @Column(name = "prf_extends")
    private String prfExtends;
    @Column(name = "prf_convert_name")
    private String prfConvertName;
    @Column(name = "prf_storage")
    private String prfStorage;
    @Column(name = "prf_origin_name")
    private String prfOriginName;
    @Column(name = "mem_code")
    private int memCode;

    public SettingProfile() {
    }


        public SettingProfile prfCode(int prfCode) {
            this.prfCode = prfCode;
            return this;
        }

        public SettingProfile prfPath(String prfPath) {
            this.prfPath = prfPath;
            return this;
        }

        public SettingProfile prfExtends(String prfExtends) {
            this.prfExtends = prfExtends;
            return this;
        }

        public SettingProfile prfConvertName(String prfConvertName) {
            this.prfConvertName = prfConvertName;
            return this;
        }

        public SettingProfile prfStorage(String prfStorage) {
            this.prfStorage = prfStorage;
            return this;
        }

        public SettingProfile prfOriginName(String prfOriginName) {
            this.prfOriginName = prfOriginName;
            return this;
        }

        public SettingProfile memCode(int memCode) {
            this.memCode = memCode;
            return this;
        }

        public SettingProfile build() {
            return new SettingProfile(prfCode, prfPath, prfExtends, prfConvertName, prfStorage, prfOriginName, memCode);
        }

}
