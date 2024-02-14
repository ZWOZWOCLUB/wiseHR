package com.wisehr.wisehr.schedule.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Embeddable
public class SchedulePatternDayID implements Serializable {
    @Column(name = "day_code")
    private int dayCode;
    @Column(name = "wok_code")
    private int wokCode;
}
