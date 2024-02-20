package com.wisehr.wisehr.schedule.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "pattern_day")
@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class ScheduleSearchPatternDay {
    @Column(name = "day_code")
    private int dayCode;
    @Id
    @Column(name = "wok_code")
    private int wokCode;

//    @ManyToOne
//    @JoinColumn(name = "wok_code")
//    private List<ScheduleSearchPatternDay> patternDayList;



}
