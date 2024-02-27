package com.wisehr.wisehr.alarmAndMessage.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AAMApprovalDTO {

    private String payCode;
    private String payDate;
    private String payKind;
    private int memCode;
    private String payName;
}
