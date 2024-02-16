package com.wisehr.wisehr.mypage.dto;

import com.wisehr.wisehr.common.DateToStringConverter;
import jakarta.persistence.Convert;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MPCertificateDTO {

    private String cerCode;
    private String cerName;
    private String cerKind;
    @Convert(converter= DateToStringConverter.class)
    private String cerDay;
    @Convert(converter= DateToStringConverter.class)
    private String cerEndDate;
    private String cerDescription;
    private String cerInstitution;
    private int memCode;
}
