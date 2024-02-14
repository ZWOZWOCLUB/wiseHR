package com.wisehr.wisehr.mypage.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MPDepartmentDTO {
    private int depCode;
    private String depName;
    private String depBirthDate;
    private String depDeleteStatus;
}
