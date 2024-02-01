package com.wisehr.wisehr.setting.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SettingDegreeFileDTO {
    private int degAtcCode;
    private String degAtcName;
    private String degAtcExtends;
    private String degAtcRegistDate;
    private String degAtcDeleteStatus;
    private String degAtcPath;
    private String degAtcConvertName;
    private String degCode;
}
