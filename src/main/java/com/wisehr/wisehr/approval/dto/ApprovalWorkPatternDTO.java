package com.wisehr.wisehr.approval.dto;

import lombok.*;

import java.sql.Time;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class ApprovalWorkPatternDTO {
    private Long wokCode;
    private Time wokStartTime;
    private Time wokEndTime;
    private String wokDeleteState;
}
