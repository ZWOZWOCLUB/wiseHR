package com.wisehr.wisehr.organization.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TreeMemDTO {
    private int memCode;
    private String memName;
    private String memStatus;
    private String posName;

    public TreeMemDTO(int memCode, String memName, String posName) {
        this.memCode = memCode;
        this.memName = memName;
        this.posName = posName;
    }
}
