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
    @Column(name = "mem_code")
    private int memCode;
    @Column(name = "sal_code")
    private String salCode;
    @Column(name = "sal_number")
    private String salNumber;
    @Column(name = "sal_bank_name")
    private String salBankName;

}
