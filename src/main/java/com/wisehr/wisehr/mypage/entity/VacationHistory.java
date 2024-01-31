package com.wisehr.wisehr.mypage.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "vacation_history")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VacationHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vhi_code")
    private int vhiCode;
    @Column(name = "mem_code")
    private int memCode;
    @Column(name = "vhi_spend")
    private int vhiSpend;
    @Column(name = "app_code")
    private String appCode;

    @Override
    public String toString() {
        return "VacationHistory{" +
                "memCode=" + memCode +
                ", vhiSpend=" + vhiSpend +
                ", appCode='" + appCode + '\'' +
                '}';
    }
}
