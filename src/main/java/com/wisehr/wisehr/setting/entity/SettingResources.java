package com.wisehr.wisehr.setting.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@ToString
@Table(name = "member")
public class SettingResources {
    @Id
    @Column(name = "mem_code")
    private int memCode;
    @OneToMany
    @JoinColumn(name = "mem_code")
    private List<SettingCareer> careerDTO;
    @OneToMany
    @JoinColumn(name = "mem_code")
    private List<SettingCertificate> certificateDTO;
    @OneToMany
    @JoinColumn(name = "mem_code")
    private List<SettingDegree> degreeDTO;
    @OneToMany
    @JoinColumn(name = "mem_code")
    private List<SettingDocumentFile> documentFiles;
    @OneToMany
    @JoinColumn(name = "mem_code")
    private List<SettingSalary> salary;

}
