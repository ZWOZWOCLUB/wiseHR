package com.wisehr.wisehr.approval.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.sql.Date;

@Entity
@Table(name = "annual")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class ApprovalAnnual {
    @Id
    @GeneratedValue(generator = "eegenerator")
    @GenericGenerator(name = "eegenerator",
            parameters = @org.hibernate.annotations.Parameter(name = "prefix", value = "vac"),
            strategy = "com.wisehr.wisehr.common.MyGenerator")
    @Column(name = "vac_code")
    private String vacCode;
    @Column(name = "vac_kind")
    private String vacKind;
    @Column(name = "vac_contents")
    private String vacContents;
    @Column(name = "vac_start_date")
    private String vacStartDate;
    @Column(name = "vac_end_date")
    private String vacEndDate;
    @OneToOne
    @JoinColumn(name = "pay_code")
    private Approval approval;
}
