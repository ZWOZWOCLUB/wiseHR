package com.wisehr.wisehr.mypage.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MPApprovalPaymentDTO {

    private String appCode;
    private int memCode;
    private String appState;
    private String appDate;
    private String appComment;
    private String payCode;
}
