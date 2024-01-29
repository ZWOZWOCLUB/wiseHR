package com.wisehr.wisehr.payment.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.sql.Date;

@Entity
@Table(name = "retirement")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RetirePayment {
    @Id
    @GeneratedValue(generator = "eegenerator")
    @GenericGenerator(name = "eegenerator",
            parameters = @org.hibernate.annotations.Parameter(name = "prefix", value = "tir"),
            strategy = "com.wisehr.wisehr.common.MyGenerator")
    @Column(name="tir_code")
    private String tirCode;
    @Column(name="tir_date")
    private Date tirDate;
    @Column(name="tir_contents")
    private String tirContents;
    @OneToOne
    @JoinColumn(name="pay_code")
    private Payment payCode;
}
