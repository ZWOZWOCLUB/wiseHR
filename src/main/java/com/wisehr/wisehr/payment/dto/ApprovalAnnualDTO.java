package com.wisehr.wisehr.payment.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class ApprovalAnnualDTO {
    private String vacCode;
    private String vacKind;
    private String vacName;
    private String vacContents;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+9")
    private Date vacStartDate;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+9")
    private Date vacEndDate;
    private ApprovalDTO approval;
}
