package com.wisehr.wisehr.pay.entity;

import com.wisehr.wisehr.pay.dto.PaySalaryDTO;
import jakarta.persistence.*;
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
    @Column(name = "mem_code", insertable = false, updatable = false) //
    private int memCode;
    @OneToOne
    @JoinColumn(name = "mem_code")
    private PaySalary salCode;

}
