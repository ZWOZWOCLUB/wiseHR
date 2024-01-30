package com.wisehr.wisehr.schedule.entity;

import jakarta.persistence.*;
import lombok.*;

@IdClass(SchedulePatternDayID.class)
@Entity
@Table(name = "pattern_day")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SchedulePatternDay {
    @Id
    @Column(name = "day_code")
    private int dayCode;
    @Id
    @Column(name = "wok_code")
    private int wokCode;
}
