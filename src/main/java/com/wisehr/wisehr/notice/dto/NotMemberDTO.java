package com.wisehr.wisehr.notice.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class NotMemberDTO {
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
