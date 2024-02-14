package com.wisehr.wisehr.schedule.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

//@IdClass(SchedulePatternDayID.class)
@Entity
@Table(name = "pattern_day")
@Getter
@Setter
@AllArgsConstructor
@ToString
public class SchedulePatternDay {
    @EmbeddedId
    private SchedulePatternDayID patternDayID;



    @ManyToOne
    @JoinColumn(name = "day_code", insertable = false, updatable = false)
    private ScheduleWeekDay weekDay;
    public SchedulePatternDay() {
    }

}
