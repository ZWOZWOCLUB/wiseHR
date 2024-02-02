package com.wisehr.wisehr.organization.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "member")
@AllArgsConstructor
@Getter
@ToString
@Setter
public class OrgMember {

    @Id
    @Column(name = "mem_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int memCode;
    @Column(name = "mem_name", nullable = false)
    private String memName;
    @Column(name = "mem_phone", nullable = false)
    private String memPhone;
    @Column(name = "mem_email", nullable = false)
    private String memEmail;
    @Column(name = "mem_status")
    private String memStatus;
    @Column(name = "mem_role", nullable = false)
    private String memRole;


    @ManyToOne
    @JoinColumn(name = "dep_code")
    @JsonIgnore
    private OrgDepartmentAndOrgMember orgDepAndOrgMem;

    @OneToOne
    @JoinColumn(name = "pos_code")
    private OrgPosition orgPosition;


    public OrgMember() {
    }

//
//
//    public OrgMember memCode(int memCode) {
//        this.memCode = memCode;
//        return this;
//    }
//
//    public OrgMember memName(String memName) {
//        this.memName = memName;
//        return this;
//    }
//
//    public OrgMember memPhone(String memPhone) {
//        this.memPhone = memPhone;
//        return this;
//    }
//
//    public OrgMember memEmail(String memEmail) {
//        this.memEmail = memEmail;
//        return this;
//    }
//
//    public OrgMember memStatus(String memStatus) {
//        this.memStatus = memStatus;
//        return this;
//    }
//
//    public OrgMember memRole(String memRole) {
//        this.memRole = memRole;
//        return this;
//    }
//
//    public OrgMember orgDepAndOrgMem(OrgDepartmentAndOrgMember orgDepAndOrgMem) {
//        this.orgDepAndOrgMem = orgDepAndOrgMem;
//        return this;
//    }
//
//    public OrgMember orgPosition(OrgPosition orgPosition) {
//        this.orgPosition = orgPosition;
//        return this;
//    }
//
//    public OrgMember build() {
//        return new com.wisehr.wisehr.organization.entity.OrgMember(memCode, memName, memPhone, memEmail, memStatus, memRole, orgDepAndOrgMem, orgPosition);
//    }


    public void setOrgDepAndOrgMem(OrgDepartmentAndOrgMember orgDepAndOrgMem) {
        this.orgDepAndOrgMem = orgDepAndOrgMem;
    }
}
