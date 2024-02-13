package com.wisehr.wisehr.organization.dto;

import lombok.*;

import java.util.List;
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TreeDepDTO {

    private Integer depCode;
    private String depName;
    private String depDeleteStatus;
    private List<TreeDepDTO> children;
    private List<TreeMemDTO> memberList;

    public TreeDepDTO(Integer depCode, String depName) {
        this.depCode = depCode;
        this.depName = depName;
    }
}
