package com.wisehr.wisehr.approval.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.sql.Date;

@Entity
@Table(name = "approval")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Approval {
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
    @JsonIgnore
    private ApprovalMember approvalMember;
}
