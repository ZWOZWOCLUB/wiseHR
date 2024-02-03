package com.wisehr.wisehr.organization.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "member")
public class OrgMemAndOrgDep {

    //멤버 전체 조회용 엔티티

    @Id
    @Column(name = "mem_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int memCode;
    @Column(name = "mem_name", nullable = false)
    private String memName;
    @Column(name = "mem_status")
    private String memStatus;


    @OneToOne
    @JoinColumn(name = "pos_code")
    private OrgPosition orgPosition;

    @OneToOne
    @JoinColumn(name = "dep_code")
    private OrgDepartment orgDepartment;


}
