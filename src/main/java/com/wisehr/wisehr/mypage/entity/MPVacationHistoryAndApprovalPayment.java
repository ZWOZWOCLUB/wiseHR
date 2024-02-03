package com.wisehr.wisehr.mypage.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "vacation_history")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MPVacationHistoryAndApprovalPayment {
    @Id
    @Column(name = "vhi_code")
    private int vhiCode;
    @Column(name = "mem_code")
    private int memCode;
    @Column(name = "vhi_spend")
    private int vhiSpend;
    @JoinColumn(name = "app_code")
    @OneToOne
    private MPMyPageApprovalPayment appCode;
}
