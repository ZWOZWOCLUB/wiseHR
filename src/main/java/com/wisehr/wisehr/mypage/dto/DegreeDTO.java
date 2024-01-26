package com.wisehr.wisehr.mypage.dto;


import lombok.*;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class DegreeDTO {

    private String degCode;
    private String degKind;
    private String degMajor;
    private String degName;
    private Date degGraduation;
    private char degState;
    private Date degAdmissions;
    private int memCode;
}
