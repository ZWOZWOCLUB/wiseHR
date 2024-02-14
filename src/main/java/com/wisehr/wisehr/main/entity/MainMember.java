package com.wisehr.wisehr.main.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
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
    @Column(name = "mem_status")
    private String memStatus;
    @Column(name = "dep_code")
    private Long depCode;

    @OneToMany(mappedBy = "mainMember")
    private List<MainAppCom> appComList;

    @OneToMany(mappedBy = "memCode")
    private List<MainSchAllow> mainSchMem;

    @ManyToOne
    @JoinColumn(name = "dep_code", insertable = false, updatable = false)
    private MainDepartment mainDepartment;

    @Override
    public String toString() {
        return "MainMember{" +
                "memCode=" + memCode +
                ", memName='" + memName + '\'' +
                ", memStatus='" + memStatus + '\'' +
                ", depCode=" + depCode +
                ", appComList=" + appComList +
                ", mainSchMem=" + mainSchMem +
//                ", mainDepartment=" + mainDepartment +
                '}';
    }
}
