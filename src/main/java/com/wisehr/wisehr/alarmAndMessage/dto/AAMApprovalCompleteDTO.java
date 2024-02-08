package com.wisehr.wisehr.alarmAndMessage.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AAMApprovalCompleteDTO {

    private String appCode;
    private int memCode;
    private String appState;
    private String appDate;
    private String appComment;
    private String payCode;
    private int perArmCode;
}
