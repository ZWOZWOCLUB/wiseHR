package com.wisehr.wisehr.mypage.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class VacationHistoryAndApprovalPaymentDTO {

    private int vhiCode;
    private int vhiSpend;
    private ApprovalPaymentDTO appCode;
    private int memCode;
}
