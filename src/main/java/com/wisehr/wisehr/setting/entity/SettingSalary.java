package com.wisehr.wisehr.setting.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "salary")
@AllArgsConstructor
@Getter
@Setter
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
    @OneToOne(mappedBy = "settingSalary", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "sal_code", insertable = false, updatable = false)
    private SettingSalaryFile salaryFile;

    public SettingSalary() {

    }

    @Override
    public String toString() {
        return "SettingSalary{" +
                "salCode='" + salCode + '\'' +
                ", salNumber='" + salNumber + '\'' +
                ", salBankName='" + salBankName + '\'' +
                ", salaryFile=" + salaryFile +
                '}';
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