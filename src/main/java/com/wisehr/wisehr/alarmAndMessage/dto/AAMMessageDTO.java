package com.wisehr.wisehr.alarmAndMessage.dto;

import com.wisehr.wisehr.alarmAndMessage.entity.AAMRecMessenger;
import lombok.*;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AAMMessageDTO {

    private int msgCode;
    private AAMRecMessengerDTO aamRecMessenger;
    private Date msgDate;
    private String msgContents;
    private int memCode;
    private String msgDeleteStatus;

}
