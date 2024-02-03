package com.wisehr.wisehr.mypage.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name ="member")
@AllArgsConstructor
@Getter
@ToString
public class MPMyPageMember {
    @Id
    @Column(name = "mem_code", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int memCode;
    @Column(name = "mem_name")
    private String memName;
    @Column(name = "mem_phone")
    private String memPhone;
    @Column(name = "mem_email")
    private String memEmail;
    @Column(name = "mem_address")
    private String memAddress;
    @Column(name = "mem_birth")
    private String memBirth;
    @Column(name = "mem_password")
    private String memPassword;
    @Column(name = "mem_hire_date")
    private String memHireDate;
    @Column(name = "mem_end_date")
    private String memEndDate;
    @Column(name = "mem_status")
    private String memStatus;
    @Column(name = "mem_role")
    private String memRole;

    public MPMyPageMember() {
    }

    public MPMyPageMember memCode(int memCode) {
        this.memCode = memCode;
        return this;
    }
    public MPMyPageMember memName(String memName) {
        this.memName = memName;
        return this;
    }
    public MPMyPageMember memPhone(String memPhone) {
        this.memPhone = memPhone;
        return this;
    }
    public MPMyPageMember memEmail(String memEmail) {
        this.memEmail = memEmail;
        return this;
    }
    public MPMyPageMember memAddress(String memAddress) {
        this.memAddress = memAddress;
        return this;
    }
    public MPMyPageMember memBirth(String memBirth) {
        this.memBirth = memBirth;
        return this;
    }
    public MPMyPageMember memPassword(String memPassword) {
        this.memPassword = memPassword;
        return this;
    }
    public MPMyPageMember memHireDate(String memHireDate) {
        this.memHireDate = memHireDate;
        return this;
    }
    public MPMyPageMember memEndDate(String memEndDate) {
        this.memEndDate = memEndDate;
        return this;
    }
    public MPMyPageMember memStatus(String memStatus) {
        this.memStatus = memStatus;
        return this;
    }
    public MPMyPageMember memRole(String memRole) {
        this.memRole = memRole;
        return this;
    }
    public MPMyPageMember build(){
        return new MPMyPageMember(memCode, memName, memPhone,
                memEmail, memAddress, memBirth,
                memPassword, memHireDate,
                memEndDate, memStatus,
                memRole);
    }
}
