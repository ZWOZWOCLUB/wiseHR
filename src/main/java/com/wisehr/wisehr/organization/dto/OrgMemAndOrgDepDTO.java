package com.wisehr.wisehr.organization.dto;


import com.wisehr.wisehr.organization.entity.OrgDepartment;
import com.wisehr.wisehr.organization.entity.OrgPosition;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrgMemAndOrgDepDTO {

    //멤버 전체 조회용 DTO

    private int memCode;
    private String memName;
//    private String memPhone;
//    private String memEmail;
    private String memStatus;
//    private String memRole;

    private OrgPosition orgPosition;
    private OrgDepartment orgDepartment;



}
