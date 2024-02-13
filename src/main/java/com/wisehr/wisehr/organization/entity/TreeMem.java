package com.wisehr.wisehr.organization.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "member")
public class TreeMem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mem_code")
    private int memCode;
    @Column(name = "mem_name",nullable = false)
    private String memName;
    @Column(name = "mem_status")
    private String memStatus;
    @Column(name = "mem_role", nullable = false)
    private String memRole;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dep_code")
    private TreeDep treeDep;
//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "pos_code")
//    private TreePos treePos;
    @Column(name = "pos_code")
    private int treePos;


}
