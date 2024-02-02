package com.wisehr.wisehr.payment.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ApprovalRetiredDTO {
    private String tirCode;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+9")
    private Date tirDate;
    private String tirContents;
    private ApprovalDTO approval;
}
