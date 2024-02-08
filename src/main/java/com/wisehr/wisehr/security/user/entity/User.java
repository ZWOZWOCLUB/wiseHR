package com.wisehr.wisehr.security.user.entity;

import com.wisehr.wisehr.security.common.ZzclubRole;
import jakarta.persistence.*;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="member")
public class User {

    @Id
    @Column(name="MEM_CODE")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int memCode;

    @Column(name="MEM_NAME")
    private String memName;

    @Column(name="MEM_PHONE")
    private String memPhone;

    @Column(name="MEM_EMAIL")
    private String memEmail;

    @Column(name="MEM_ADDRESS")
    private String memAddress;

    @Column(name="MEM_BIRTH")
    private String memBirth;

    @Column(name="MEM_PASSWORD")
    private String memPassword;

    @Column(name="MEM_HIRE_DATE")
    private String memHireDate;

    @Column(name="MEM_END_DATE")
    private String memEndDate;

    @Column(name="MEM_STATUS")
    private String memStatus;

    @Enumerated(value = EnumType.STRING)
    @Column(name="MEM_ROLE")
    private ZzclubRole memRole;

    public User() {
    }

    public int getMemCode() {
        return memCode;
    }

    public void setMemCode(int mem_code) {
        this.memCode = mem_code;
    }

    public String getMemName() {
        return memName;
    }

    public void setMemName(String mem_name) {
        this.memName = mem_name;
    }

    public String getMemPhone() {
        return memPhone;
    }

    public void setMemPhone(String mem_phone) {
        this.memPhone = mem_phone;
    }

    public String getMemEmail() {
        return memEmail;
    }

    public void setMemEmail(String mem_email) {
        this.memEmail = mem_email;
    }

    public String getMemAddress() {
        return memAddress;
    }

    public void setMemAddress(String mem_address) {
        this.memAddress = mem_address;
    }

    public String getMemBirth() {
        return memBirth;
    }

    public void setMemBirth(String mem_birth) {
        this.memBirth = mem_birth;
    }

    public String getMemPassword() {
        return memPassword;
    }

    public void setMemPassword(String mem_password) {
        this.memPassword = mem_password;
    }

    public String getMemHireDate() {
        return memHireDate;
    }

    public void setMemHireDate(String mem_hire_date) {
        this.memHireDate = mem_hire_date;
    }

    public String getMemEndDate() {
        return memEndDate;
    }

    public void setMemEndDate(String mem_end_date) {
        this.memEndDate = mem_end_date;
    }

    public String getMemStatus() {
        return memStatus;
    }

    public void setMemStatus(String mem_status) {
        this.memStatus = mem_status;
    }

    public ZzclubRole getMemRole() {
        return memRole;
    }

    public void setMemRole(ZzclubRole mem_role) {
        this.memRole = mem_role;
    }

    public List<String> getRoleList() {
        if(this.memRole.getMemRole().length() > 0) {
            return Arrays.asList(this.memRole.getMemRole().split(","));
        }

        return new ArrayList<>();
    }

    @Override
    public String toString() {
        return "User{" +
                "memCode=" + memCode +
                ", memName='" + memName + '\'' +
                ", memPhone='" + memPhone + '\'' +
                ", memEmail='" + memEmail + '\'' +
                ", memAddress='" + memAddress + '\'' +
                ", memBirth='" + memBirth + '\'' +
                ", memPassword='" + memPassword + '\'' +
                ", memHireDate='" + memHireDate + '\'' +
                ", memEndDate='" + memEndDate + '\'' +
                ", memStatus='" + memStatus + '\'' +
                ", memRole=" + memRole +
                '}';
    }
}
