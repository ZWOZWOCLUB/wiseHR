package com.wisehr.wisehr.mypage.dto;

import lombok.*;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class DocumentFileDTO {
    private int docAtcCode;
    private String docAtcExtends;
    private String docAtcConvertName;
    private String docAtcRegistDate;
    private String docAtcStorage;
    private String docAtcDeleteStatus;
    private String docAtcPath;
    private long memCode;
    private String docAtcOriginName;
}
