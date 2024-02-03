package com.wisehr.wisehr.organization.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name="department")
@AllArgsConstructor
@Getter
@Setter
public class OrgDepartmentAndOrgMember {


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

    @OneToMany(mappedBy = "orgDepAndOrgMem")
    private List<OrgMember> memberList;


    public OrgDepartmentAndOrgMember() {
    }



    public OrgDepartmentAndOrgMember depCode(int depCode) {
        this.depCode = depCode;
        return this;
    }

    public OrgDepartmentAndOrgMember depName(String depName) {
        this.depName = depName;
        return this;
    }

    public OrgDepartmentAndOrgMember refDepCode(Integer refDepCode) {
        this.refDepCode = refDepCode;
        return this;
    }

    public OrgDepartmentAndOrgMember depBirthDate(String depBirthDate) {
        this.depBirthDate = depBirthDate;
        return this;
    }

    public OrgDepartmentAndOrgMember depDeleteStatus(String depDeleteStatus) {
        this.depDeleteStatus = depDeleteStatus;
        return this;
    }

    public OrgDepartmentAndOrgMember memberList(List<OrgMember> memberList) {
        this.memberList = memberList;
        return this;
    }

    public OrgDepartmentAndOrgMember build() {
        return new OrgDepartmentAndOrgMember(depCode, depName, refDepCode, depBirthDate, depDeleteStatus, memberList);
    }

    @Override
    public String toString() {
        return "OrgDepartmentAndOrgMember{" +
                "depCode=" + depCode +
                ", depName='" + depName + '\'' +
                ", refDepCode=" + refDepCode +
                ", depBirthDate='" + depBirthDate + '\'' +
                ", depDeleteStatus='" + depDeleteStatus + '\'' +
                ", memberList=" + memberList +
                '}';
    }
}





