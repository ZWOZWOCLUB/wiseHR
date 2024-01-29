package com.wisehr.wisehr.pay.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "member")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PayMemPosSal {
    @Id
    @Column(name = "mem_code", nullable = false)
    private String memCode;
    @OneToOne
    @JoinColumn(name = "pos_code")
    private PayPosition posCode;
    @OneToOne
    @JoinColumn(name = "sal_code")
    private PaySalary salCode;
}
