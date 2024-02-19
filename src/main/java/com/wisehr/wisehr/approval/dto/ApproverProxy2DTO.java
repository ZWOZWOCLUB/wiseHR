package com.wisehr.wisehr.approval.dto;


import lombok.*;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = "approvalMembers")
public class ApproverProxy2DTO {
    private Long proCode;
    private String roleMember;
    private String proMember;
    private Date proStartDate;
    private Date proEndDate;
    private String proMemRole;
}
