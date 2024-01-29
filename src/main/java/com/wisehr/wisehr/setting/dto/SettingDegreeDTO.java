package com.wisehr.wisehr.setting.dto;

import lombok.*;

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
}
