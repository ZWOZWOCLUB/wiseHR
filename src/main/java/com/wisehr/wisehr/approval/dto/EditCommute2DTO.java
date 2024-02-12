package com.wisehr.wisehr.approval.dto;

import lombok.*;

import java.sql.Time;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class EditCommute2DTO {
    private String ediCode;
    private String ediKind;
    private String ediContents;
//    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+9")
    private String ediDate;
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+9")
    private String ediTime;
    private ApprovalDTO approval;
    private ApprovalMemberDTO cMember;
    private List<ReferencerDTO> rMember;
}
