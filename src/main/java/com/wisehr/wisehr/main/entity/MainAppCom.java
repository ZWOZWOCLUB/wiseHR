package com.wisehr.wisehr.main.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "approval_complete")
public class MainAppCom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "app_code")
    private String appCode;
    @Column(name = "app_state", nullable = false)
    private String appState;
    @Column(name = "app_date", nullable = false)
    private LocalDate appDate;

    @ManyToOne
    @JoinColumn(name = "mem_code")
    private MainMember mainMember;

    @Override
    public String toString() {
        return "MainAppCom{" +
                "appCode='" + appCode + '\'' +
                ", appState='" + appState + '\'' +
                ", appDate=" + appDate +
//                ", mainMember=" + mainMember +
                '}';
    }
}
