package com.wisehr.wisehr.setting.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "department")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SettingDepartment {
    @Id
    @Column(name = "dep_code", nullable = false)
    private int depCode;
    @Column(name = "dep_name")
    private String depName;
    @Column(name = "ref_dep_code")
    private int refDepCode;
    @Column(name = "dep_birth_date")
    private String depBirthDate;
    @Column(name = "dep_delete_status")
    private String depDeleteStatus;
}
