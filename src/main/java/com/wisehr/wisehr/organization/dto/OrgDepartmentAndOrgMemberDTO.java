package com.wisehr.wisehr.organization.dto;
import com.wisehr.wisehr.organization.entity.OrgMember;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrgDepartmentAndOrgMemberDTO {

    private int depCode;
    private String depName;
    private Integer refDepCode;
    private String depBirthDate;
    private String depDeleteStatus;

    private List<OrgMember> memberList;


}
