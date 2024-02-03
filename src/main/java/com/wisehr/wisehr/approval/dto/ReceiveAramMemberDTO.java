package com.wisehr.wisehr.approval.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString(exclude = "approvalMembers")
public class ReceiveAramMemberDTO {
    private Long memCode;
    private String memName;
    private String memPhone;
    private String memEmail;
    private String memAddress;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+9")
    private Date memBirth;
    private String memPassword;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+9")
    private Date memHireDate;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+9")
    private Date memEndDate;
    private String memStatus;
    private String memRole;
    private ApprovalDepAndMemDTO depCode;
}
