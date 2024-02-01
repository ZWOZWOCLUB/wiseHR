package com.wisehr.wisehr.setting.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SettingDocumentFileDTO {
    private int docAtcCode;
    private String docAtcExtends;
    private String docAtcConvertName;
    private String docAtcRegistDate;
    private String docAtcStorage;
    private String docAtcDeleteStatus;
    private String docAtcPath;
    private String docAtcOriginName;
    private String docAtcKind;
    private int memCode;
}
