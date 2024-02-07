package com.wisehr.wisehr.pay.dto;

import lombok.*;

import java.util.List;

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
    private int deductedAmount;
    private int totalDeduction;
    private int totalDdeSalary;
    private int totalDeductedAmount;
    private int totalDeductions;
    private PaySalaryDTO salCode;

}
