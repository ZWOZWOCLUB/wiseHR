package com.wisehr.wisehr.approval.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class ApprovalDTO {
    private String payCode;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+9")
    private Date payDate;
    private String payName;
    private String payKind;
    private ApprovalMemberDTO approvalMember;
}
