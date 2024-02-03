package com.wisehr.wisehr.schedule.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
@Entity
@Table(name = "etc_pattern")
@AllArgsConstructor
@Getter
@ToString
public class ScheduleEtcPattern {
    @Id
    @Column(name = "etc_code")
    public int etcCode;
    @Column(name = "mem_code")
    public int memCode;
    @Column(name = "etc_date")
    public String etcDate;
    @Column(name = "etc_kind")
    public String etcKind;

    public ScheduleEtcPattern() {
    }




    public ScheduleEtcPattern etcCode(int etcCode) {
        this.etcCode = etcCode;
        return this;
    }

    public ScheduleEtcPattern memCode(int memCode) {
        this.memCode = memCode;
        return this;
    }

    public ScheduleEtcPattern etcDate(String etcDate) {
        this.etcDate = etcDate;
        return this;
    }

    public ScheduleEtcPattern etcKind(String etcKind) {
        this.etcKind = etcKind;
        return this;
    }

    public ScheduleEtcPattern build() {
        return new ScheduleEtcPattern(etcCode, memCode, etcDate, etcKind);
    }

}
