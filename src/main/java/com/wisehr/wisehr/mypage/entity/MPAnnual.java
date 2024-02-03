package com.wisehr.wisehr.mypage.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "annual")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MPAnnual {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vac_code")
    private String vacCode;
    @Column(name = "vac_kind")
    private String vacKind;
    @Column(name = "vac_name")
    private String vacName;
    @Column(name = "vac_contents")
    private String vacContents;
    @Column(name = "vac_start_date")
    private String vacStartDate;
    @Column(name = "vac_end_date")
    private String vacEndDate;
    @Column(name = "pay_code")
    private String payCode;

    @Override
    public String toString() {
        return "Annual{" +
                "vacCode='" + vacCode + '\'' +
                ", vacKind='" + vacKind + '\'' +
                ", vacName='" + vacName + '\'' +
                ", vacContents='" + vacContents + '\'' +
                ", vacStartDate='" + vacStartDate + '\'' +
                ", vacEndDate='" + vacEndDate + '\'' +
                ", payCode='" + payCode + '\'' +
                '}';
    }
}
