package com.wisehr.wisehr.approval.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wisehr.wisehr.approval.entity.ApprovalMember;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@Getter
@ToString(exclude = "approvalMembers")
public class ApprovalPosAndMemDTO {
    private Long posCode;
    private String posName;
    private Long posSalary;

    private List<ApprovalMember> approvalMembers;

    public ApprovalPosAndMemDTO() {
    }

    public ApprovalPosAndMemDTO posCode(Long posCode){
        this.posCode = posCode;
        return this;
    }

    public ApprovalPosAndMemDTO posName(String posName){
        this.posName = posName;
        return this;
    }
    public ApprovalPosAndMemDTO posSalary(Long posSalary){
        this.posSalary = posSalary;
        return this;
    }

    public ApprovalPosAndMemDTO build(){
        return new ApprovalPosAndMemDTO(posCode, posName, posSalary, approvalMembers);
    }
}
