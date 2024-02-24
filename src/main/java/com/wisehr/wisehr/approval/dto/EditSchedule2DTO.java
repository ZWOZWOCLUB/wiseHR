package com.wisehr.wisehr.approval.dto;


import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class EditSchedule2DTO {
    private String eshCode;
//    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+9")
    private String eshStartDate;    //
//    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+9")
    private String eshEndDate;  //
    private String eshContents; //
    private ApprovalDTO approval;
    private String eshDateType;
    private String eshOffStartDate; //
    private String eshOffEndDate;   //
    private ApprovalMemberDTO cMember;
    private List<String> rMember;
}
