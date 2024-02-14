package com.wisehr.wisehr.pay.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "member")
public class PayMember {
    @Id
    @Column(name = "mem_code")
    private int memCode;
    @Column(name = "mem_hire_date")
    private String memHireDate;
}
