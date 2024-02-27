package com.wisehr.wisehr.alarmAndMessage.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "approval")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AAMApproval {

    @Id
    @Column(name = "pay_code")
    private String payCode;
    @Column(name = "pay_date")
    private String payDate;
    @Column(name = "pay_kind")
    private String payKind;
    @Column(name = "mem_code")
    private int memCode;
    @Column(name = "pay_name")
    private String payName;

}
