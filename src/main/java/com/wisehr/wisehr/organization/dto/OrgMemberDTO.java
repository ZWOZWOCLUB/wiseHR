package com.wisehr.wisehr.organization.dto;

import com.wisehr.wisehr.organization.entity.OrgDepartment;
import com.wisehr.wisehr.organization.entity.OrgPosition;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    private OrgDepartmentAndOrgMemberDTO orgDepAndOrgMem;
    private OrgPosition orgPosition;
}
