package com.wisehr.wisehr.mypage.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MPPassDTO {

    private int memCode;
    private String originMemPassword;
    private String newMemPassword1;
    private String newMemPassword2;
}
