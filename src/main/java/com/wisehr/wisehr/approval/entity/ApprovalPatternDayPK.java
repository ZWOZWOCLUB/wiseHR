package com.wisehr.wisehr.approval.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ApprovalPatternDayPK implements Serializable {
    @Column(name = "day_code")
    Long dayCode;
    @Column(name = "wok_code")
    Long wokCode;

    @Override
    public int hashCode() {
        return Objects.hash(dayCode, wokCode);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApprovalPatternDayPK approvalPatternDayPK = (ApprovalPatternDayPK) o;
        return Objects.equals(dayCode, approvalPatternDayPK.dayCode) && Objects.equals(wokCode, approvalPatternDayPK.wokCode);
    }

    public ApprovalPatternDayPK() {
    }

    public ApprovalPatternDayPK(Long dayCode, Long wokCode) {
        this.dayCode = dayCode;
        this.wokCode = wokCode;
    }

    @Override
    public String toString() {
        return "ApprovalPatternDayPK{" +
                "dayCode=" + dayCode +
                ", wokCode=" + wokCode +
                '}';
    }

    public Long getDayCode() {
        return dayCode;
    }

    public void setDayCode(Long dayCode) {
        this.dayCode = dayCode;
    }

    public Long getWokCode() {
        return wokCode;
    }

    public void setWokCode(Long wokCode) {
        this.wokCode = wokCode;
    }
}
