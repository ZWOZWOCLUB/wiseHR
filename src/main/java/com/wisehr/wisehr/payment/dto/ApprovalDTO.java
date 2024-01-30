package com.wisehr.wisehr.payment.dto;


import lombok.*;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class ApprovalDTO {
    private String payCode;
    private Date payDate;
    private String payKind;
    private ApprovalMemberDTO paymentMember;
}
