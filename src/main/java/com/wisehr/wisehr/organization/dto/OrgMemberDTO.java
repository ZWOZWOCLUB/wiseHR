package com.wisehr.wisehr.organization.dto;

import com.wisehr.wisehr.organization.entity.OrgDepartment;
import com.wisehr.wisehr.organization.entity.OrgPosition;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.util.List;

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
    private String memRole;

    private OrgDepartmentAndOrgMemberDTO orgDepAndOrgMem;
    private OrgPositionDTO orgPosition;

    private List<Integer> memberCodes; //부서에 멤버 추가하기 메소드 용도(멤버코드들 묶음)
}
