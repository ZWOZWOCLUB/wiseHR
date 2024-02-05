package com.wisehr.wisehr.schedule.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Embeddable
public class ScheduleInsertPatternDayID implements Serializable {
    private int dayCode;
    private int wokCode;
}
