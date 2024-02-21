package com.wisehr.wisehr.alarmAndMessage.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AAMRecMessageDTO {

    private int msgCode;
    private AAMSendMessengerDTO aamSendMessenger;
    private int memCode;
    private String recMsgDeleteStatus;
    private String recMsgCheckStatus;

}
