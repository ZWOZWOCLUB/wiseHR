package com.wisehr.wisehr.dataFormat.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class DataPositionDTO {
    private Long posCode;
    private String posName;
    private Long posSalary;
}
