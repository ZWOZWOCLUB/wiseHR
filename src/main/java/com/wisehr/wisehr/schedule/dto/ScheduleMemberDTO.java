package com.wisehr.wisehr.schedule.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ScheduleMemberDTO {
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
    private int posCode;
    private int depCode;
}
