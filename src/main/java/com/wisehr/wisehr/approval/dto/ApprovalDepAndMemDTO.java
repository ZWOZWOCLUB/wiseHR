package com.wisehr.wisehr.approval.dto;


import com.wisehr.wisehr.approval.entity.ApprovalMember;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ApprovalDepAndMemDTO {

    private int depCode;
    private String depName;
    private Integer refDepCode;
    private String depBirthDate;
    private String depDeleteStatus;

    private List<ApprovalMember> approvalMembers;

}
