package com.wisehr.wisehr.mypage.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MPVacationHistoryAndApprovalPaymentDTO {

    private int vhiCode;
    private int vhiSpend;
    private MPApprovalPaymentDTO appCode;
    private int memCode;
}
