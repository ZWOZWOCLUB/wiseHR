package com.wisehr.wisehr.comment.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "member")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class ComMember {
    @Id
    @Column(name = "mem_code", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memCode;
    @Column(name = "mem_name", nullable = false)
    private String memName;
    @Column(name = "mem_phone", nullable = false)
    private String memPhone;
    @Column(name = "mem_email", nullable = false)
    private String memEmail;
    @Column(name = "mem_address", nullable = false)
    private String memAddress;
    @Column(name = "mem_birth", nullable = false)
    private String memBirth;
    @Column(name = "mem_password", nullable = false)
    private String memPassword;
    @Column(name = "mem_hire_date", nullable = false)
    private String memHireDate;
    @Column(name = "mem_end_date", nullable = true)
    private String memEndDate;
    @Column(name = "mem_status", nullable = false)
    private String memStatus;
    @Column(name = "mem_role", nullable = false)
    private String memRole;
    @Column(name = "dep_code", nullable = true)
    private Long depCode;
    @OneToOne
    @JoinColumn(name = "pos_code", nullable = true)
    private ComPosition posCode;
}
