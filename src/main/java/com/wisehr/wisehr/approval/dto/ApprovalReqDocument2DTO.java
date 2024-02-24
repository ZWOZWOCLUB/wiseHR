package com.wisehr.wisehr.approval.dto;


import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ApprovalReqDocument2DTO {
    private String reqCode;
    private String reqKind;
    private String reqUse;
    private ApprovalDTO approval;
    private ApprovalMemberDTO cMember;
    private List<String> rMember;
}
