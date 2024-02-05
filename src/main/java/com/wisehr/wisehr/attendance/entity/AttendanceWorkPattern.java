package com.wisehr.wisehr.attendance.entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Entity
@Table(name = "work_pattern")
public class AttendanceWorkPattern {
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
}
