package com.wisehr.wisehr.attendance.dto;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class AttendanceDateDTO {
    private String currentDate;
    private String startTime;
    private String endTime;
    private String memName;
    private String posName;
}
