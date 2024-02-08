package com.wisehr.wisehr.alarmAndMessage.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AAMRecMessageDTO {

    private String msgCode;
    private AAMSendMessengerDTO aamSendMessenger;
    private int memCode;
}
