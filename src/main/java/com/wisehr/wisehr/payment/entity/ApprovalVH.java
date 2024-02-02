package com.wisehr.wisehr.payment.entity;


import com.wisehr.wisehr.payment.dto.ApprovalCompleteDTO;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Entity
@Table(name = "vacation_history")
public class ApprovalVH {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vhi_code")
    private Long vhiCode;
    @Column(name = "vhi_spend")
    private int vhiSpend;
    @OneToOne
    @JoinColumn(name = "app_code")
    private ApprovalComplete appCode;
    @OneToOne
    @JoinColumn(name = "mem_code")
    private ApprovalMember approvalMember;
}
