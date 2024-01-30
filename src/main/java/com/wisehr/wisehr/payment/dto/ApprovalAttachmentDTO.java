package com.wisehr.wisehr.payment.dto;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ApprovalAttachmentDTO {
    private Long payAtcCode;
    private String payAtcName;
    private String payAtcDeleteStatus;
    private String payAtcPath;
    private ApprovalDTO payment;

    @Override
    public String toString() {
        return "ApprovalAttachmentDTO{" +
                "payAtcCode=" + payAtcCode +
                ", payAtcName='" + payAtcName + '\'' +
                ", payAtcDeleteStatus='" + payAtcDeleteStatus + '\'' +
                ", payAtcPath='" + payAtcPath + '\'' +
                '}';
    }
}
