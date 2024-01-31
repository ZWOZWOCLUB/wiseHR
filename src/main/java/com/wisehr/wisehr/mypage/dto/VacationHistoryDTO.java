package com.wisehr.wisehr.mypage.dto;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class VacationHistoryDTO {

    private int vhiCode;
    private int vhiSpend;
    private String appCode;
    private int memCode;
}
