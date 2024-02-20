package com.wisehr.wisehr.attendance.dto;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@ToString
@Getter
public class TodayInfoDTO {

    private int completeNumber;
    private int nagativeNumber;
    private int stayNumber;
    private int referencerNumber;
    private String startTime;
    private String endTime;
    private int attValue;
    private String attStartTime;
    private String attEndTime;
}
