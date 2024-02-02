package com.wisehr.wisehr.mypage.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

@Entity
@Table(name ="member")
@AllArgsConstructor
@Getter
@ToString
public class MyPageMember {
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

    public MyPageMember() {
    }

    public MyPageMember memCode(int memCode) {
        this.memCode = memCode;
        return this;
    }
    public MyPageMember memName(String memName) {
        this.memName = memName;
        return this;
    }
    public MyPageMember memPhone(String memPhone) {
        this.memPhone = memPhone;
        return this;
    }
    public MyPageMember memEmail(String memEmail) {
        this.memEmail = memEmail;
        return this;
    }
    public MyPageMember memAddress(String memAddress) {
        this.memAddress = memAddress;
        return this;
    }
    public MyPageMember memBirth(String memBirth) {
        this.memBirth = memBirth;
        return this;
    }
    public MyPageMember memPassword(String memPassword) {
        this.memPassword = memPassword;
        return this;
    }
    public MyPageMember memHireDate(String memHireDate) {
        this.memHireDate = memHireDate;
        return this;
    }
    public MyPageMember memEndDate(String memEndDate) {
        this.memEndDate = memEndDate;
        return this;
    }
    public MyPageMember memStatus(String memStatus) {
        this.memStatus = memStatus;
        return this;
    }
    public MyPageMember memRoll(String memRoll) {
        this.memRoll = memRoll;
        return this;
    }
    public MyPageMember build(){
        return new MyPageMember(memCode, memName, memPhone,
                memEmail, memAddress, memBirth,
                memPassword, memHireDate,
                memEndDate, memStatus,
                memRoll);
    }
}
