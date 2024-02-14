package com.wisehr.wisehr.approval.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class EditCommuteDTO {
    private String ediCode;
    private String ediKind;
    private String ediContents;
//    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+9")
    private String ediDate;
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+9")
    private String ediTime;
    private ApprovalDTO approval;
}
