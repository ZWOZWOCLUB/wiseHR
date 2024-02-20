package com.wisehr.wisehr.alarmAndMessage.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AAMRecMessengerDTO {
    private Integer recMsgCode;
    private int msgCode;
    private int memCode;
    private String recMsgDeleteStatus;
    private String recMsgCheckStatus;
    private AAMMemberDTO aamMember;
}
