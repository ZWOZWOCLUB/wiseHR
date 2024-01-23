package com.wisehr.wisehr.pay.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "salary")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PaySalary {
    @Id
    @Column(name = "sal_code", nullable = false)
    private String salCode;
    @Column(name = "sal_number")
    private String salNumber;
    @Column(name = "sal_bank_name")
    private String salBankName;
    @Column(name = "mem_code")
    private int memCode;
}
