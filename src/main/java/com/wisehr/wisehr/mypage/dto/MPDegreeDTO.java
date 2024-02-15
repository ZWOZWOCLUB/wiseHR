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
public class MPDegreeDTO {

    private String degCode;
    private String degKind;
    private String degMajor;
    private String degName;
    @Convert(converter= DateToStringConverter.class)
    private String degGraduation;
    private char degState;
    @Convert(converter= DateToStringConverter.class)
    private String degAdmissions;
    private int memCode;
}
