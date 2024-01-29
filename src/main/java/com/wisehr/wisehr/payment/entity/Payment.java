package com.wisehr.wisehr.payment.entity;


import com.wisehr.wisehr.payment.dto.PaymentAttachmentDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "payment")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Payment {
    @Id
    @GeneratedValue(generator = "eegenerator")
    @GenericGenerator(name = "eegenerator",
            parameters = @org.hibernate.annotations.Parameter(name = "prefix", value = "pay"),
            strategy = "com.wisehr.wisehr.common.MyGenerator")
    @Column(name = "pay_code")
    private String payCode;
    @Column(name = "pay_date")
    private Date payDate;
    @Column(name = "pay_kind")
    private String payKind;
    @OneToOne
    @JoinColumn(name = "mem_code")
    private PaymentMember paymentMember;
    @OneToOne
    @JoinColumn(name = "pay_code")
    private PaymentAttachment payAttachment;
}
