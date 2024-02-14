package com.wisehr.wisehr.schedule.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Embeddable
public class ScheduleAllowanceID implements Serializable {
    @Column(name = "mem_code")
    private int memCode;
    @Column(name = "sch_code")
    private String schCode;


}
