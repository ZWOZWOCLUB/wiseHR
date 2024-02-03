package com.wisehr.wisehr.mypage.dto;

import lombok.*;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MPCareerDTO {

    private String crrCode;
    private String crrName;
    private String crrPosition;
    private Date crrStartDate;
    private Date crrEndDate;
    private String crrState;
    private String crrDescription;
    private int memCode;
}
