package com.wisehr.wisehr.schedule.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@IdClass(ScheduleInsertPatternDayID.class)
@Entity
@Table(name = "pattern_day")
@Getter
@Setter
@AllArgsConstructor
@ToString
public class ScheduleInsertPatternDay {
    @Id
    @Column(name = "day_code")
    private int dayCode;
    @Id
    @Column(name = "wok_code")
    private int wokCode;




    public ScheduleInsertPatternDay() {
    }


}
