package com.wisehr.wisehr.alarmAndMessage.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AAMMemberDTO {
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
    private String memRole;
    private int depCode;
    private int posCode;
    private String depName;
    private String posName;

}
