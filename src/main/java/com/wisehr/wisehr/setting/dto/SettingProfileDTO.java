package com.wisehr.wisehr.setting.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SettingProfileDTO {
    private int prfCode;
    private String prfPath;
    private String prfExtends;
    private String prfConvertName;
    private String prfStorage;
    private int memCode;
}
