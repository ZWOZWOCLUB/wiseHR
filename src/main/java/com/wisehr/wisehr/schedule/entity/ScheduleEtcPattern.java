package com.wisehr.wisehr.schedule.entity;

import com.wisehr.wisehr.setting.entity.SettingMemDepPos;
import com.wisehr.wisehr.setting.entity.SettingMember;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "etc_pattern")
@AllArgsConstructor
@Getter
@ToString
public class ScheduleEtcPattern {
    @Id
    @Column(name = "etc_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int etcCode;
    @Column(name = "mem_code")
    public int memCode;
    @Column(name = "etc_date")
    public String etcDate;
    @Column(name = "etc_kind")
    public String etcKind;

    @OneToOne
    @JoinColumn(name = "mem_code", insertable = false, updatable = false)
    public SettingMemDepPos member;

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

    public ScheduleEtcPattern member(SettingMemDepPos member) {
        this.member = member;
        return this;
    }



    public ScheduleEtcPattern build() {
        return new ScheduleEtcPattern(etcCode, memCode, etcDate, etcKind, member);
    }



}
