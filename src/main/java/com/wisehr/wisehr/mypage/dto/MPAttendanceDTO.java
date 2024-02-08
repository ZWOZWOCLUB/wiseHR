package com.wisehr.wisehr.mypage.dto;

import lombok.*;

import java.sql.Time;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MPAttendanceDTO {
    private int attCode;
    private Time attStartTime;
    private Time attEndTime;
    private String attStatus;
    private java.sql.Date attWorkDate;
    private int memCode;
    private String schCode;
    private String totalWork;
}
