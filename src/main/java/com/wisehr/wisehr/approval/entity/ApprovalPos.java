package com.wisehr.wisehr.approval.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
@Table(name = "position")
public class ApprovalPos {
    @Id
    @Column(name = "pos_code")
    private Long posCode;
    @Column(name = "pos_name")
    private String posName;
    @Column(name = "pos_salary")
    private Long posSalary;
}
