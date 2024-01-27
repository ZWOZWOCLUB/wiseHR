package com.wisehr.wisehr.payment.entity;


import jakarta.persistence.*;
import lombok.*;

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
