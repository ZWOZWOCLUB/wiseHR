package com.wisehr.wisehr.approval.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class EditScheduleDTO {
    private String eshCode;
//    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+9")
    private String eshStartDate;
//    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+9")
    private String eshEndDate;
    private String eshContents;
    private ApprovalDTO approval;
    private String eshDateType;
    private String eshOffStartDate;
    private String eshOffEndDate;
}
