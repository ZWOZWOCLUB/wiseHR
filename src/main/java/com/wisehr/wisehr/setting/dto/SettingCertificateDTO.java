package com.wisehr.wisehr.setting.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class SettingCertificateDTO {
    private String cerCode;
    private String cerName;
    private String cerKind;
    private String cerDay;
    private String cerEndDate;
    private String cerDescription;
    private String cerInstitution;
    private int memCode;

}
