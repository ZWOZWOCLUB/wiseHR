package com.wisehr.wisehr.mypage.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MPDocumentFileDTO {
    private int docAtcCode;
    private String docAtcExtends;
    private String docAtcConvertName;
    private String docAtcRegistDate;
    private String docAtcStorage;
    private String docAtcDeleteStatus;
    private String docAtcPath;
    private long memCode;
    private String docAtcOriginName;
    private String docAtcKind;
}
