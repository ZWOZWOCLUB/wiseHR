package com.wisehr.wisehr.mypage.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "approval_complete")
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class MPMyPageApprovalPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "app_code")
    private String appCode;
    @Column(name = "mem_code")
    private int memCode;
    @Column(name = "app_state")
    private String appState;
    @Column(name = "app_date")
    private String appDate;
    @Column(name = "app_comment")
    private String appComment;
    @Column(name = "pay_code")
    private String payCode;
}
