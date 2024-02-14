package com.wisehr.wisehr.setting.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Embeddable
public class SettingAllowanceID implements Serializable {
    @Column(name = "mem_code")
    private int memCode;
    @Column(name = "sch_code")
    private String schCode;


}
