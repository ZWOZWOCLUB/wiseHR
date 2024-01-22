package com.wisehr.wisehr.setting.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SettingProfileDTO {
    private int prf_code;
    private String prf_path;
    private String prf_extends;
    private String prf_convert_name;
    private String prf_storage;
    private int memCode;
}
