package com.wisehr.wisehr.attendance.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class MainScheduleAllowanceId implements Serializable {
    @Column(name = "mem_code")
    private Long memCode;
    @Column(name = "sch_code")
    private String schCode;

    //여기가 embeddedid가 선언된 클래스의 pk

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MainScheduleAllowanceId that = (MainScheduleAllowanceId) o;
        return Objects.equals(memCode, that.memCode) && Objects.equals(schCode, that.schCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memCode, schCode);
    }
}
