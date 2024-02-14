package com.wisehr.wisehr.organization.dto;


import com.wisehr.wisehr.organization.entity.OrgDepartment;
import com.wisehr.wisehr.organization.entity.OrgPosition;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrgMemAndOrgDepDTO {

    //멤버 전체 조회용 DTO

    private int memCode;
    private String memName;
    private String memStatus;
    private String memRole;

    private int depCode; //부서코드 단일로 받아주기 위해 추가(권한부여 메소드)

    private OrgPosition orgPosition;
    private OrgDepartment orgDepartment;





}
