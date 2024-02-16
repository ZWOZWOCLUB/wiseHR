package com.wisehr.wisehr.approval.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "position")
@AllArgsConstructor
@Getter
@ToString(exclude = "approvalMembers")
public class ApprovalPosAndMem {
    @Id
    @Column(name = "pos_code")
    private Long posCode;
    @Column(name = "pos_name")
    private String posName;
    @Column(name = "pos_salary")
    private Long posSalary;

    @OneToMany(mappedBy = "position")
    @JsonIgnore
    private List<ApprovalMember> approvalMembers;

    public ApprovalPosAndMem() {
    }

    public ApprovalPosAndMem posCode(Long posCode){
        this.posCode = posCode;
        return this;
    }

    public ApprovalPosAndMem posName(String posName){
        this.posName = posName;
        return this;
    }
    public ApprovalPosAndMem posSalary(Long posSalary){
        this.posSalary = posSalary;
        return this;
    }

    public ApprovalPosAndMem build(){
        return new ApprovalPosAndMem(posCode, posName, posSalary, approvalMembers);
    }
}
