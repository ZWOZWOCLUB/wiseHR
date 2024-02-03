package com.wisehr.wisehr.attendance.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class AttendanceWorkPatternDTO {
    private Long wokCode;
    private String wokStartTime;
    private String wokRestTime;
    private String wokEndTime;
    private String wokDeleteState;
}
