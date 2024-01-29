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
    private int basicSalary;
    private int foodExpenses;
    private int nationalPension;
    private int healthInsurance;
    private int employmentInsurance;
    private int incomeTax;
    private int localIncomeTax;
    private int medicalInsurance;
}
