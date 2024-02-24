package com.wisehr.wisehr.approval.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class AppRefDTO {
    ApprovalDTO approvalComplete;
    List<ApprovalMemberDTO> refMember;
}
