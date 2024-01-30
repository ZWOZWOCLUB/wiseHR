package com.wisehr.wisehr.payment.entity;

import com.wisehr.wisehr.payment.dto.PaymentDTO;
import com.wisehr.wisehr.payment.dto.PaymentMemberDTO;
import com.wisehr.wisehr.payment.dto.PaymentPerAlarmDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

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
    @GeneratedValue(generator = "eegenerator")
    @GenericGenerator(name = "eegenerator",
    parameters = @org.hibernate.annotations.Parameter(name = "prefix", value = "app"),
    strategy = "com.wisehr.wisehr.common.MyGenerator")
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
    private Payment payment;
    @OneToOne
    @JoinColumn(name = "mem_code")
    private PaymentMember paymentMember;
    @OneToOne
    @JoinColumn(name = "per_arm_code")
    private PaymentPerAlarm perArmCode;
}
