package com.wisehr.wisehr.setting.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SettingAttendanceDTO {
    private int attCode;
    private String attStartTime;
    private String attEndTime;
    private String attStatus;
    private String attWorkDate;
    private int memCode;
    private String schCode;
}
