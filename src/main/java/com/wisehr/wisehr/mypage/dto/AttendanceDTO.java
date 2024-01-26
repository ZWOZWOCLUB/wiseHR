package com.wisehr.wisehr.mypage.dto;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AttendanceDTO {
    private int attCode;
    private Date attStartTime;
    private Date attEndTime;
    private String attStatus;
    private java.sql.Date attWorkDate;
    private int memCode;
    private String schCode;
}
