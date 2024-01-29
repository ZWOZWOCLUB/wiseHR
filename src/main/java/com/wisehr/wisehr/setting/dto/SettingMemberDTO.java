package com.wisehr.wisehr.setting.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SettingMemberDTO {
    private int memCode;
    private String memName;
    private String memPhone;
    private String memEmail;
    private String memAddress;
    private String memBirth;
    private String memPassword;
    private String memHireDate;
    private String memEndDate;
    private String memStatus;
    private String memRoll;
    private int posCode;
    private int depCode;
}
