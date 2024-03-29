package com.wisehr.wisehr.approval.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "member")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
//@ToString(exclude = "approvalMembers" )
public class ApprovalMember2 {
    @Id
    @Column(name = "mem_code", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memCode;
    @Column(name = "mem_name")
    private String memName;
    @Column(name = "mem_phone")
    private String memPhone;
    @Column(name = "mem_email")
    private String memEmail;
    @Column(name = "mem_address")
    private String memAddress;
    @Column(name = "mem_birth")
    private String memBirth;
    @Column(name = "mem_password")
    private String memPassword;
    @Column(name = "mem_hire_date")
    private String memHireDate;
    @Column(name = "mem_end_date")
    private String memEndDate;
    @Column(name = "mem_status")
    private String memStatus;
    @Column(name = "mem_role")
    private String memRole;
    @OneToOne
    @JoinColumn(name = "dep_code")
//    @JsonIgnore
    private ApprovalDepAndMem department;
    @OneToOne
    @JoinColumn(name = "pos_code")
//    @JsonIgnore
    private ApprovalPosAndMem position;
}
