package com.wisehr.wisehr.mypage.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "certificate")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class MPCertificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cer_code")
    private String cerCode;

    @Column(name = "cer_name")
    private String cerName;

    @Column(name = "cer_kind")
    private String cerKind;

    @Column(name = "cer_day")
    private String cerDay;

    @Column(name = "cer_end_date")
    private String cerEndDate;

    @Column(name = "cer_description")
    private String cerDescription;

    @Column(name = "cer_institution")
    private String cerInstitution;

    @Column(name = "mem_code")
    private int memCode;
}
