package com.wisehr.wisehr.mypage.dto;

import com.wisehr.wisehr.common.DateToStringConverter;
import jakarta.persistence.Convert;
import lombok.*;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MPCareerDTO {

    private String crrCode;
    private String crrName;
    private String crrPosition;
    @Convert(converter= DateToStringConverter.class)
    private String crrStartDate;
    @Convert(converter= DateToStringConverter.class)
    private String crrEndDate;
    private String crrState;
    private String crrDescription;
    private int memCode;
}
