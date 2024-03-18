package com.wisehr.wisehr.setting.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;

@Entity
@Table(name = "salary")
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SettingSalary {
    @Id
    @GeneratedValue(generator = "eegenerator")
    @GenericGenerator(name = "eegenerator",
            parameters = @org.hibernate.annotations.Parameter(name = "prefix", value = "sal"),
            strategy = "com.wisehr.wisehr.common.MyGenerator")
    @Column(name = "sal_code")
    private String salCode;
    @Column(name = "sal_number")
    private String salNumber;
    @Column(name = "sal_bank_name")
    private String salBankName;
    @Column(name = "mem_code")
    private int memCode;
    @OneToMany
    @JoinColumn(name = "sal_code", insertable = false, updatable = false)
    private List<SettingSalaryFile> salaryFile;


    public SettingSalary() {

    }


    public SettingSalary(String salCode, String salNumber, String salBankName, int memCode) {
    }




        public SettingSalary salCode(String salCode) {
            this.salCode = salCode;
            return this;
        }

        public SettingSalary salNumber(String salNumber) {
            this.salNumber = salNumber;
            return this;
        }

        public SettingSalary salBankName(String salBankName) {
            this.salBankName = salBankName;
            return this;
        }

        public SettingSalary memCode(int memCode) {
            this.memCode = memCode;
            return this;
        }

        public SettingSalary build() {
            return new SettingSalary(salCode, salNumber, salBankName, memCode);
        }

}