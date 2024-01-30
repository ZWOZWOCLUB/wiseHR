package com.wisehr.wisehr.payment.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.sql.Date;
import java.sql.Time;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class EditCommuteDTO {
    private String ediCode;
    private String ediKind;
    private String ediName;
    private String ediContents;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+9")
    private Date ediDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+9")
    private Time ediTime;
    private ApprovalDTO approval;
}
