package com.wisehr.wisehr.pay.entity;

import com.wisehr.wisehr.setting.dto.SettingDepartmentDTO;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "member")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PayMember {
    @Id
    @Column(name = "mem_code", nullable = false)
    private String memCode;
    @OneToOne
    @JoinColumn(name = "pos_code")
    private PayPosition posCode;
    @OneToOne
    @JoinColumn(name = "sal_code")
    private PaySalary salCode;
}
