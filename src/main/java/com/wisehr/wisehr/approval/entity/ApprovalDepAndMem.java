package com.wisehr.wisehr.approval.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "department")
@AllArgsConstructor
@Getter
@ToString(exclude = "approvalMembers")
public class ApprovalDepAndMem {


    @Id
    @Column(name = "dep_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long depCode;
    @Column(name = "dep_name", nullable = false)
    private String depName;
    @Column(name = "ref_dep_code")
    private Integer refDepCode;
    @Column(name = "dep_birth_date", nullable = false)
    private String depBirthDate;
    @Column(name = "dep_delete_status", nullable = false)
    private String depDeleteStatus;

    @OneToMany(mappedBy = "department")
    @JsonIgnore
    private List<ApprovalMember> approvalMembers;


    public ApprovalDepAndMem() {
    }

    public ApprovalDepAndMem depCode(Long depCode) {
        this.depCode = depCode;
        return this;
    }

    public ApprovalDepAndMem depName(String depName) {
        this.depName = depName;
        return this;
    }

    public ApprovalDepAndMem refDepCode(Integer refDepCode) {
        this.refDepCode = refDepCode;
        return this;
    }

    public ApprovalDepAndMem depBirthDate(String depBirthDate) {
        this.depBirthDate = depBirthDate;
        return this;
    }

    public ApprovalDepAndMem depDeleteStatus(String depDeleteStatus) {
        this.depDeleteStatus = depDeleteStatus;
        return this;
    }

    public ApprovalDepAndMem approvalMembers(List<ApprovalMember> approvalMembers) {
        this.approvalMembers = approvalMembers;
        return this;
    }

    public ApprovalDepAndMem build() {
        return new ApprovalDepAndMem(depCode, depName, refDepCode, depBirthDate, depDeleteStatus, approvalMembers);
    }

}
