package com.wisehr.wisehr.setting.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name ="member")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SettingMemDepPos {
    @Id
    @Column(name = "mem_code", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int memCode;
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
    private SettingDepartment departmentDTO;
    @OneToOne
    @JoinColumn(name = "pos_code")
    private SettingPosition positionDTO;

}
