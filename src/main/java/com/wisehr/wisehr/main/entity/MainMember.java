package com.wisehr.wisehr.main.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Table(name = "member")
public class MainMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mem_code")
    private Long memCode;
    @Column(name = "mem_name")
    private String memName;
    @Column(name = "dep_code")
    private Long depCode;

    @OneToMany(mappedBy = "mainMember")
    List<MainAppCom> appComList;
}
