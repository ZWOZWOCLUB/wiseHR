package com.wisehr.wisehr.attendance.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MainAttendanceDTO {

    private Long attCode;
    private LocalTime attStartTime;
    private LocalTime attEndTime;
    private String attStatus;
    private LocalDate attWorkDate;
    private MainScheduleAllowanceDTO memCode;
    private MainScheduleAllowanceDTO schCode;

}
