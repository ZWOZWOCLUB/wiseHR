package com.wisehr.wisehr.pay.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "paydetails")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PayDetails {
    @Id
    @Column(name = "pde_code")
    private String pdeCode;
    @Column(name = "pde_date")
    private String pdeDate;
    @Column(name = "pde_salary")
    private int pdeSalary;
    @Column(name = "pde_yymm")
    private String pdeYymm;
    @Column(name = "mem_code")
    private int memCode;
}
