package com.wisehr.wisehr.schedule.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "pattern_day")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SchedulePatternDay {
    @Id
    @Column(name = "day_code")
    private int dayCode;
    @Column(name = "wor_code")
    private int workCode;
}
