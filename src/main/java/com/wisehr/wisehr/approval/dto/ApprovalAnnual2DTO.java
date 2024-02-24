package com.wisehr.wisehr.approval.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
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
//    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+9")
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String vacStartDate;
//    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+9")
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String vacEndDate;
    private ApprovalDTO approval;
    private ApprovalMemberDTO cMember;
    private List<String> rMember;
}
