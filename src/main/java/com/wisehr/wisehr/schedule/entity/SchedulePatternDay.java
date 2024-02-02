package com.wisehr.wisehr.schedule.entity;

import jakarta.persistence.*;
import lombok.*;

@IdClass(SchedulePatternDayID.class)
@Entity
@Table(name = "pattern_day")
@Getter
@AllArgsConstructor
@ToString
public class SchedulePatternDay {
    @Id
    @Column(name = "day_code")
    private int dayCode;
    @Id
    @Column(name = "wok_code")
    private int wokCode;
    @ManyToOne
    @JoinColumn(name = "day_code")
    private ScheduleAllSelect scheduleAllSelect;


    public SchedulePatternDay() {
    }

    public SchedulePatternDay(int dayCode, int wokCode) {
    }


    public SchedulePatternDay dayCode(int dayCode) {
            this.dayCode = dayCode;
            return this;
        }

        public SchedulePatternDay wokCode(int wokCode) {
            this.wokCode = wokCode;
            return this;
        }

        public SchedulePatternDay build() {
            return new SchedulePatternDay(dayCode, wokCode);
        }

}
