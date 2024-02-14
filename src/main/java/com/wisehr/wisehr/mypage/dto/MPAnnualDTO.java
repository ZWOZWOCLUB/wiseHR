package com.wisehr.wisehr.mypage.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MPAnnualDTO {

    private String vacCode;
    private String vacKind;
    private String vacName;
    private String vacContents;
    private String vacStartDate;
    private String vacEndDate;
    private String payCode;
}
