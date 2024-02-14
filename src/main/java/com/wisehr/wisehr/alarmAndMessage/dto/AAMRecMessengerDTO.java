package com.wisehr.wisehr.alarmAndMessage.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AAMRecMessengerDTO {
    private String msgCode;
    private int memCode;
    private AAMMemberDTO aamMember;
}
