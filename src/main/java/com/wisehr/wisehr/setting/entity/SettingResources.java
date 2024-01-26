package com.wisehr.wisehr.setting.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
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

    @Override
    public String toString() {
        return "SettingResources{" +
                "memCode=" + memCode +
                ", careerDTO=" + careerDTO +
                ", certificateDTO=" + certificateDTO +
                ", degreeDTO=" + degreeDTO +
                '}';
    }
}
