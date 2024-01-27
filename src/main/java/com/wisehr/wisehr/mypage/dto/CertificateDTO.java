package com.wisehr.wisehr.mypage.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CertificateDTO {

    private String cerCode;
    private String cerName;
    private String cerKind;
    private String cerDay;
    private String cerEndDate;
    private String cerDescription;
    private String cerInstitution;
    private int memCode;
}
