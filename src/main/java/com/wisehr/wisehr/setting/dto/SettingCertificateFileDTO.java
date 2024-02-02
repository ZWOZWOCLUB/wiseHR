package com.wisehr.wisehr.setting.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SettingCertificateFileDTO {
    private int cerAtcCode;
    private String cerAtcName;
    private String cerAtcExtends;
    private String cerAtcRegistDate;
    private String cerAtcDeleteStatus;
    private String cerAtcPath;
    private String cerAtcConvertName;
    private String cerCode;
}
