package com.wisehr.wisehr.setting.entity;

import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name = "profile")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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
    @Column(name = "mem_code")
    private int memCode;
}
