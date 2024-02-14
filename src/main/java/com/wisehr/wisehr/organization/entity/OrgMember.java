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

    @Override
    public String toString() {
        return "OrgMember{" +
                "memCode=" + memCode +
                ", memName='" + memName + '\'' +
                ", memPhone='" + memPhone + '\'' +
                ", memEmail='" + memEmail + '\'' +
                ", memStatus='" + memStatus + '\'' +
                ", memRole='" + memRole + '\'' +
//                ", orgDepAndOrgMem=" + orgDepAndOrgMem +
                ", orgPosition=" + orgPosition +
                '}';
    }


    public void setOrgDepAndOrgMem(OrgDepartmentAndOrgMember orgDepAndOrgMem) {
        this.orgDepAndOrgMem = orgDepAndOrgMem;


    }
}
