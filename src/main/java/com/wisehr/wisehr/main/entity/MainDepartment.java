package com.wisehr.wisehr.main.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "department")
public class MainDepartment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dep_code")
    private Long depCode;
    @Column(name = "dep_name")
    private String depName;
    @Column(name = "dep_delete_status")
    private String deleteStatus;

    @OneToMany(mappedBy = "mainDepartment")
    private List<MainMember> mainMemberList;



}
