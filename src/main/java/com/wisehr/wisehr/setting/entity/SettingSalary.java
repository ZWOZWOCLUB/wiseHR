package com.wisehr.wisehr.setting.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "salary")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SettingSalary {
    @Id
    @Column(name = "sal_code")
    private String salCode;
    @Column(name = "sal_number")
    private String salNumber;
    @Column(name = "sal_bank_name")
    private String salBankName;
    @Column(name = "mem_code")
    private int memCode;

    @Override
    public String toString() {
        return "SettingSalary{" +
                "salCode='" + salCode + '\'' +
                ", salNumber='" + salNumber + '\'' +
                ", salBankName='" + salBankName + '\'' +
                '}';
    }
}