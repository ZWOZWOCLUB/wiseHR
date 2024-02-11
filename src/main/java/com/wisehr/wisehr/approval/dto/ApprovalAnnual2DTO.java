package com.wisehr.wisehr.approval.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.sql.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class ApprovalAnnual2DTO {
    private String vacCode;
    private String vacKind;
    private String vacContents;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+9")
    private Date vacStartDate;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+9")
    private Date vacEndDate;
    private ApprovalDTO approval;
    private ApprovalMemberDTO Cmember;
    private List<ReferencerDTO> Rmember;
}
