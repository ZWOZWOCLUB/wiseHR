package com.wisehr.wisehr.setting.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name ="member")
@AllArgsConstructor
@Getter
@ToString
public class SettingMember {
    @Id
    @Column(name = "mem_code", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int memCode;
    @Column(name = "mem_name", nullable = false)
    private String memName;
    @Column(name = "mem_phone", nullable = false)
    private String memPhone;
    @Column(name = "mem_email", nullable = false)
    private String memEmail;
    @Column(name = "mem_address", nullable = false)
    private String memAddress;
    @Column(name = "mem_birth", nullable = false)
    private String memBirth;
    @Column(name = "mem_password", nullable = false)
    private String memPassword;
    @Column(name = "mem_hire_date", nullable = false)
    private String memHireDate;
    @Column(name = "mem_end_date", nullable = true)
    private String memEndDate;
    @Column(name = "mem_status", nullable = false)
    private String memStatus;
    @Column(name = "mem_role", nullable = false)
    private String memRole;
    @Column(name = "dep_code", nullable = true)
    private int depCode;
    @Column(name = "pos_code", nullable = true)
    private int posCode;

    public SettingMember() {
    }




    public SettingMember memCode(int memCode) {
        this.memCode = memCode;
        return this;
    }

    public SettingMember memName(String memName) {
        this.memName = memName;
        return this;
    }

    public SettingMember memPhone(String memPhone) {
        this.memPhone = memPhone;
        return this;
    }

    public SettingMember memEmail(String memEmail) {
        this.memEmail = memEmail;
        return this;
    }

    public SettingMember memAddress(String memAddress) {
        this.memAddress = memAddress;
        return this;
    }

    public SettingMember memBirth(String memBirth) {
        this.memBirth = memBirth;
        return this;
    }

    public SettingMember memPassword(String memPassword) {
        this.memPassword = memPassword;
        return this;
    }

    public SettingMember memHireDate(String memHireDate) {
        this.memHireDate = memHireDate;
        return this;
    }

    public SettingMember memEndDate(String memEndDate) {
        this.memEndDate = memEndDate;
        return this;
    }

    public SettingMember memStatus(String memStatus) {
        this.memStatus = memStatus;
        return this;
    }

    public SettingMember memRole(String memRole) {
        this.memRole = memRole;
        return this;
    }

    public SettingMember depCode(int depCode) {
        this.depCode = depCode;
        return this;
    }

    public SettingMember posCode(int posCode) {
        this.posCode = posCode;
        return this;
    }



    public SettingMember build() {
            return new SettingMember(memCode, memName, memPhone, memEmail, memAddress, memBirth, memPassword, memHireDate, memEndDate, memStatus, memRole, depCode, posCode);
    }



}
