package com.wisehr.wisehr.setting.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SettingDegreeDTO {
    private String degCode;
    private String degKind;
    private String degMajor;
    private String degName;
    private String degGraduation;
    private String degState;
    private String degAdmissions;
    private int memCode;
    private List<SettingDegreeFileDTO> degreeFile;
}
