package com.wisehr.wisehr.alarmAndMessage.dto;

import com.wisehr.wisehr.common.DateToStringConverter;
import jakarta.persistence.Convert;
import lombok.*;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AAMSendMessengerDTO {
    private String msgCode;
    @Convert(converter= DateToStringConverter.class)
    private String msgDate;
    private String msgContents;
    private int memCode;
    private AAMMemberDTO aamMember;
    private String msgDeleteStatus;
}
