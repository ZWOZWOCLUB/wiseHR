package com.wisehr.wisehr.alarmAndMessage.dto;

import com.wisehr.wisehr.alarmAndMessage.entity.AAMRecMessenger;
import com.wisehr.wisehr.common.DateToStringConverter;
import jakarta.persistence.Convert;
import lombok.*;

import java.sql.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AAMMessageDTO {

    private int msgCode;
    private List<AAMRecMessengerDTO> aamRecMessenger;
    @Convert(converter= DateToStringConverter.class)
    private String msgDate;
    private String msgContents;
    private int memCode;
    private String msgDeleteStatus;

}
