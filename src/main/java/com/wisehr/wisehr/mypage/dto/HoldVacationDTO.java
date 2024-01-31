package com.wisehr.wisehr.mypage.dto;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class HoldVacationDTO {

    private long memCode;
    private int vctCount;
    private int vctDeadline;
}
