package com.wisehr.wisehr.alarmAndMessage.dto;

import lombok.*;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SendMessengerDTO {
    private String msgCode;
    private Date msgDate;
    private String msgContents;
    private int memCode;
    private String msgDeleteStatus;
}
