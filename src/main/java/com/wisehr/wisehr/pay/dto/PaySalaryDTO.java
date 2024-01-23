package com.wisehr.wisehr.pay.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PaySalaryDTO {
    private String salNumber;
    private String salBankName;
    private int memCode;
}
