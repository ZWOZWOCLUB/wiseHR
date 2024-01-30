package com.wisehr.wisehr.organization.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name="department")
@AllArgsConstructor
@Getter
@ToString
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
}
