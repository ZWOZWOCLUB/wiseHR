package com.wisehr.wisehr.approval.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Entity
@Table(name = "department")
public class ApprovalDep {
    @Id
    @Column(name = "dep_code")
    private Long depCode;
    @Column(name = "dep_name")
    private String depName;
    @Column(name = "ref_dep_code")
    private Long RefDepCode;
    @Column(name = "dep_birth_date")
    private Date depBirthDate;
    @Column(name = "dep_delete_status")
    private String depDeleteStatus;
}
