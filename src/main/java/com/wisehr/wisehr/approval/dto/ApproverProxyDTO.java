package com.wisehr.wisehr.approval.dto;


import lombok.*;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = "approvalMembers")
public class ApproverProxyDTO {
    private Long proCode;
    private ApprovalMemberDTO roleMember;
    private ApprovalProxyMemberDTO proMember;
    private Date proStartDate;
    private Date proEndDate;
    private String proMemRole;
}
