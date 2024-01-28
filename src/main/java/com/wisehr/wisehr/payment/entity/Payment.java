package com.wisehr.wisehr.payment.entity;


import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

@Entity
@Table(name = "payment")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Payment {
    @Id
    @Column(name = "pay_code")
    private String payCode;
    @Column(name = "pay_date")
    private Date payDate;
    @Column(name = "pay_kind")
    private String payKind;
    @ManyToOne
    @JoinColumn(name = "mem_code")
    private PaymentMember memCode;
}
