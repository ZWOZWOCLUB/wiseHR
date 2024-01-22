package com.wisehr.wisehr.setting.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SettingDepartmentDTO {
    private int depCode;
    private String depName;
    private int refDepCode;
    private String depBirthDate;
    private String depDeleteStatus;
}
