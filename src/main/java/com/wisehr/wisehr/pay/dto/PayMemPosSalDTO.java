package com.wisehr.wisehr.pay.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PayMemPosSalDTO {
    private String memCode;
    private PayPositionDTO posCode;
    private PaySalaryDTO salCode;
}
