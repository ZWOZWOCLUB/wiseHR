package com.wisehr.wisehr.alarmAndMessage.dto;

import lombok.*;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AAMSendMessengerDTO {
    private String msgCode;
    private Date msgDate;
    private String msgContents;
    private Long memCode;
    private String msgDeleteStatus;
}
