package com.wisehr.wisehr.schedule.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "work_pattern")
public class ScheduleSearchWorkPattern {
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
    @Column(name = "wok_type")
    private String wokType;

    @OneToMany
    @JoinColumn(name = "wok_code")
    private List<ScheduleSearchPatternDay> scheduleSearchPatternDay;


}
