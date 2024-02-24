package com.wisehr.wisehr.approval.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.sql.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ApprovalRetired2DTO {
    private String tirCode;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+9")
    private String tirDate;
    private String tirContents;
    private ApprovalDTO approval;
    private ApprovalMemberDTO cMember;
    private List<String> rMember;
}
