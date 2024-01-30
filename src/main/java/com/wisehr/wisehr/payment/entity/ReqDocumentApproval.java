package com.wisehr.wisehr.payment.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "request_document")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ReqDocumentApproval {
    @Id
    @GeneratedValue(generator = "eegenerator")
    @GenericGenerator(name = "eegenerator",
            parameters = @org.hibernate.annotations.Parameter(name = "prefix", value = "req"),
            strategy = "com.wisehr.wisehr.common.MyGenerator")
    @Column(name = "req_code")
    private String reqCode;
    @Column(name = "req_kind")
    private String reqKind;
    @Column(name = "req_use")
    private String reqUse;
    @OneToOne
    @JoinColumn(name = "pay_code")
    private Approval approval;
}
