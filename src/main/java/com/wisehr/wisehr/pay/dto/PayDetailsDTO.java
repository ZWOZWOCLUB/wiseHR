package com.wisehr.wisehr.pay.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PayDetailsDTO {
    private String pdeCode;
    private String pdeDate;
    private int pdeSalary;
    private String pdeYymm;
    private int memCode;
}
