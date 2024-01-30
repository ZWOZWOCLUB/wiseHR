package com.wisehr.wisehr.organization.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Entity
@Table(name="department")
@AllArgsConstructor
@Getter
@ToString
public class OrgDepartment {

    @Id
    @Column(name = "dep_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int depCode;
    @Column(name = "dep_name", nullable = false)
    private String depName;
    @Column(name = "ref_dep_code")
    private Integer refDepCode;
    @Column(name = "dep_birth_date", nullable = false)
    private String depBirthDate;
    @Column(name = "dep_delete_status", nullable = false)
    private String depDeleteStatus;


    public OrgDepartment() {
    }

    //빌더패턴
    public OrgDepartment depCode(int depCode) {
        this.depCode = depCode;
        return this;
    }

    public OrgDepartment depName(String depName) {
        this.depName = depName;
        return this;
    }

    public OrgDepartment refDepCode(Integer refDepCode) {
        this.refDepCode = refDepCode;
        return this;
    }

    public OrgDepartment depBirthDate(String depBirthDate) {
        this.depBirthDate = depBirthDate;
        return this;
    }

    public OrgDepartment depDeleteStatus(String depDeleteStatus) {
        this.depDeleteStatus = depDeleteStatus;
        return this;
    }

    public OrgDepartment build() {
        return new OrgDepartment(depCode, depName, refDepCode, depBirthDate, depDeleteStatus);
    }
}




