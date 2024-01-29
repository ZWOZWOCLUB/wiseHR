package com.wisehr.wisehr.organization.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrgDepartmentDTO {

    private int depCode;
    private String depName;
    private Integer refDepCode;
    private String depBirthDate;
    private String depDeleteStatus;
}
