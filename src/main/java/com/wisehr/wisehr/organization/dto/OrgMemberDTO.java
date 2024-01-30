package com.wisehr.wisehr.organization.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrgMemberDTO {

    private int memCode;
    private String memName;
    private String memPhone;
    private String memEmail;
    private String memStatus;
    private String memRoll;
}
