package com.wisehr.wisehr.payment.entity;

import com.wisehr.wisehr.payment.dto.PaymentDTO;
import com.wisehr.wisehr.payment.dto.PaymentMemberDTO;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
@Table(name = "approval_payment")
public class ApprovalPayment {
    @Id
    @Column(name = "app_code")
    private String appCode;
    @Column(name = "app_state")
    private String appState;
    @Column(name = "app_date")
    private Date appDate;
    @Column(name = "app_comment")
    private String appComment;
    @OneToOne
    @JoinColumn(name = "pay_code")
    private Payment payCode;
//    @ManyToMany
//    @JoinColumn(name = "mem_code")
//    private List<PaymentMember> memCode;
}
