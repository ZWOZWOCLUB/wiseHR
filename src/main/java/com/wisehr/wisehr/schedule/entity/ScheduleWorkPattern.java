package com.wisehr.wisehr.schedule.entity;

import jakarta.persistence.*;
import lombok.*;

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


    public ScheduleWorkPattern() {
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

        public ScheduleWorkPattern build() {
            return new ScheduleWorkPattern(wokCode, wokStartTime, wokRestTime, wokEndTime, wokDeleteState);
        }

}
