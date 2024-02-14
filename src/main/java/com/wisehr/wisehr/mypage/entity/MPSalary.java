package com.wisehr.wisehr.mypage.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "salary")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MPSalary {
    @Id
    @Column(name = "sal_code")
    private String salCode;
    @Column(name = "sal_number")
    private String salNumber;
    @Column(name = "sal_bank_name")
    private String salBankNumber;
    @Column(name = "mem_code")
    private int memCode;
}
