package com.wisehr.wisehr.pay.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PayPositionDTO {
    private int posCode;
    private String posName;
    private int posSalary;
}
