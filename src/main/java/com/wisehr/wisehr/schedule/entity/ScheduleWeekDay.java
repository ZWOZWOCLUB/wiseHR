package com.wisehr.wisehr.schedule.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "week_day")
public class ScheduleWeekDay {
    @Id
    @Column(name = "day_code")
    private int dayCode;
    @Column(name = "day_name")
    private String dayName;
}
