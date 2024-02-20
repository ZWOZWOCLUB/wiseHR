package com.wisehr.wisehr.mypage.dto;

import com.wisehr.wisehr.common.DateToStringConverter;
import jakarta.persistence.Convert;
import lombok.*;

import java.sql.Time;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MPAttendanceDTO {
    private int attCode;
    private Time attStartTime;
    private Time attEndTime;
    private String attStatus;
    @Convert(converter= DateToStringConverter.class)
    private String attWorkDate;
    private int memCode;
    private String schCode;
    private String totalWork;
}
