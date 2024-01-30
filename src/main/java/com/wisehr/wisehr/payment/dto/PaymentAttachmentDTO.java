package com.wisehr.wisehr.payment.dto;


import lombok.*;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class PaymentAttachmentDTO {
    private Long payAtcCode;
    private String payAtcName;
    private String payAtcDeleteStatus;
    private String payAtcPath;
    private PaymentDTO payment;

    @Override
    public String toString() {
        return "PaymentAttachmentDTO{" +
                "payAtcCode=" + payAtcCode +
                ", payAtcName='" + payAtcName + '\'' +
                ", payAtcDeleteStatus='" + payAtcDeleteStatus + '\'' +
                ", payAtcPath='" + payAtcPath + '\'' +
                '}';
    }
}
