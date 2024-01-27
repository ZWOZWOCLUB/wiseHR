package com.wisehr.wisehr.payment.entity;


import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "request_document")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ReqDocumentPayment {
    @Id
    @Column(name = "req_code")
    private String reqCode;
    @Column(name = "req_kind")
    private String reqKind;
    @Column(name = "req_use")
    private String reqUse;
    @OneToOne
    @Column(name = "pay_code")
    private Payment payCode;
}
