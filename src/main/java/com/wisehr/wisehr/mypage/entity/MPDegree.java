package com.wisehr.wisehr.mypage.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

@Entity
@Table(name = "degree")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class MPDegree {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "deg_code")
    private String degCode;

    @Column(name = "deg_kind")
    private String degKind;

    @Column(name = "deg_major")
    private String degMajor;

    @Column(name = "deg_name")
    private String degName;

    @Column(name = "deg_graduation")
    private Date degGraduation;

    @Column(name = "deg_state")
    private char degState;

    @Column(name = "deg_admissions")
    private Date degAdmissions;

    @Column(name = "mem_code")
    private int memCode;
}
