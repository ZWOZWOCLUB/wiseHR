package com.wisehr.wisehr.schedule.entity;

import com.wisehr.wisehr.setting.entity.SettingDepartment;
import com.wisehr.wisehr.setting.entity.SettingPosition;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name ="member")
@AllArgsConstructor
@Setter
@Getter
public class ScheduleMember {
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


    @OneToMany
    @JoinColumn(name = "mem_code", insertable = false, updatable = false)
    private List<ScheduleEtcPattern> etcPatterns;

    @OneToOne
    @JoinColumn(name = "dep_code")
    private SettingDepartment depCode;
    @OneToOne
    @JoinColumn(name = "pos_code")
    private SettingPosition posCode;

    public ScheduleMember() {
    }

    public ScheduleMember(int memCode, String memName, String memPhone, String memEmail, String memAddress, String memBirth, String memPassword, String memHireDate, String memEndDate, String memStatus, String memRole) {
    }

    public ScheduleMember(List<ScheduleEtcPattern> etcPatterns) {
        this.etcPatterns = etcPatterns;
    }

    public ScheduleMember memCode(int memCode) {
        this.memCode = memCode;
        return this;
    }

    public ScheduleMember memName(String memName) {
        this.memName = memName;
        return this;
    }

    public ScheduleMember memPhone(String memPhone) {
        this.memPhone = memPhone;
        return this;
    }

    public ScheduleMember memEmail(String memEmail) {
        this.memEmail = memEmail;
        return this;
    }

    public ScheduleMember memAddress(String memAddress) {
        this.memAddress = memAddress;
        return this;
    }

    public ScheduleMember memBirth(String memBirth) {
        this.memBirth = memBirth;
        return this;
    }

    public ScheduleMember memPassword(String memPassword) {
        this.memPassword = memPassword;
        return this;
    }

    public ScheduleMember memHireDate(String memHireDate) {
        this.memHireDate = memHireDate;
        return this;
    }

    public ScheduleMember memEndDate(String memEndDate) {
        this.memEndDate = memEndDate;
        return this;
    }

    public ScheduleMember memStatus(String memStatus) {
        this.memStatus = memStatus;
        return this;
    }

    public ScheduleMember memRole(String memRole) {
        this.memRole = memRole;
        return this;
    }





    public ScheduleMember build() {
            return new ScheduleMember(memCode, memName, memPhone, memEmail, memAddress, memBirth, memPassword, memHireDate, memEndDate, memStatus, memRole);
    }

    @Override
    public String toString() {
        return "ScheduleMember{" +
                ", memName='" + memName + '\'' +
                ", memPhone='" + memPhone + '\'' +
                ", memEmail='" + memEmail + '\'' +
                ", memAddress='" + memAddress + '\'' +
                ", memBirth='" + memBirth + '\'' +
                ", memPassword='" + memPassword + '\'' +
                ", memHireDate='" + memHireDate + '\'' +
                ", memEndDate='" + memEndDate + '\'' +
                ", memStatus='" + memStatus + '\'' +
                ", memRole='" + memRole + '\'' +
                ", depCode=" + depCode +
                ", posCode=" + posCode +
                '}';
    }
}
