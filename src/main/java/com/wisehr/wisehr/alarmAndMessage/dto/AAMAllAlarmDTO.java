package com.wisehr.wisehr.alarmAndMessage.dto;

import com.wisehr.wisehr.common.DateToStringConverter;
import jakarta.persistence.Convert;
import lombok.*;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AAMAllAlarmDTO {
    private int allArmCode;
    @Convert(converter= DateToStringConverter.class)
    private String allArmDate;
    private String allArmCheck;
    private String notCode;
    private int memCode;
}
