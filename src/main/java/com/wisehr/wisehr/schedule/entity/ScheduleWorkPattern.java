package com.wisehr.wisehr.schedule.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@Getter
@ToString
@Entity
@Table(name = "work_pattern")
public class ScheduleWorkPattern {
    @Id
    @Column(name = "wok_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int wokCode;
    @Column(name = "wok_start_time")
    private String wokStartTime;
    @Column(name = "wok_rest_time")
    private String wokRestTime;
    @Column(name = "wok_end_time")
    private String wokEndTime;
    @Column(name = "wok_delete_state")
    private String wokDeleteState;
    @Column(name = "wok_color")
    private String wokColor;
    @Column(name = "wok_type")
    private String wokType;

    @OneToMany
    @JoinColumn(name = "wok_code")
    private List<SchedulePatternDay> patternDayList;

    public ScheduleWorkPattern() {
    }

    public ScheduleWorkPattern(int wokCode, String wokStartTime, String wokRestTime, String wokEndTime, String wokDeleteState, List<SchedulePatternDay> patternDayList, String wokColor) {
    }

    public ScheduleWorkPattern wokCode(int wokCode) {
        this.wokCode = wokCode;
        return this;
    }

    public ScheduleWorkPattern wokStartTime(String wokStartTime) {
        this.wokStartTime = wokStartTime;
        return this;
    }

    public ScheduleWorkPattern wokRestTime(String wokRestTime) {
        this.wokRestTime = wokRestTime;
        return this;
    }

    public ScheduleWorkPattern wokEndTime(String wokEndTime) {
        this.wokEndTime = wokEndTime;
        return this;
    }

    public ScheduleWorkPattern wokDeleteState(String wokDeleteState) {
        this.wokDeleteState = wokDeleteState;
        return this;
    }

    public ScheduleWorkPattern patternDayList(List<SchedulePatternDay> patternDays) {
        this.patternDayList = patternDays;
        return this;
    }

    public ScheduleWorkPattern  wokColor(String  wokColor) {
        this.wokColor = wokColor;
        return this;
    }

    public ScheduleWorkPattern build() {
        return new ScheduleWorkPattern(wokCode, wokStartTime, wokRestTime, wokEndTime, wokDeleteState, patternDayList, wokColor);
    }

}
