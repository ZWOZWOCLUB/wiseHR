package com.wisehr.wisehr.payment.dto;


import com.wisehr.wisehr.payment.entity.PaymentAttachment;
import com.wisehr.wisehr.setting.dto.SettingMemberDTO;
import lombok.*;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class PaymentDTO {
    private String payCode;
    private Date payDate;
    private String payKind;
    private PaymentMemberDTO paymentMember;
}
