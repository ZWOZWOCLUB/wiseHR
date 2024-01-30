package com.wisehr.wisehr.comment.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "position")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ComPosition {
    @Id
    @Column(name = "pos_code", nullable = false)
    private int posCode;
    @Column(name = "pos_name")
    private String posName;
    @Column(name = "pos_salary")
    private int posSalary;
}
