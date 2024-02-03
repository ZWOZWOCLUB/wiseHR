package com.wisehr.wisehr.approval.entity;


import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = "approvalMembers")
@Entity
@Table(name = "proxy_approver")
public class ApproverProxy {
    @Id
    @Column(name = "pro_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long proCode;

    @OneToOne
    @JoinColumn(name = "role_mem_code", referencedColumnName = "mem_code")
    private ApprovalMember roleMember;

    @OneToOne
    @JoinColumn(name = "pro_mem_code", referencedColumnName = "mem_code")
    private ApprovalProxyMember proMember;

    @Column(name = "pro_start_date")
    private Date proStartDate;

    @Column(name = "pro_end_date")
    private Date proEndDate;
    @Column(name = "pro_mem_role")
    private String proMemRole;
}
