package com.wisehr.wisehr.approval.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.sql.Time;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Entity
@Table(name = "work_pattern")
public class ApprovalWorkPattern {
    @Id
    @Column(name = "wok_Code")
    private Long wokCode;
    @Column(name = "wok_start_time")
    private Time wokStartTime;
    @Column(name = "wok_end_time")
    private Time wokEndTime;
    @Column(name = "wok_delete_state")
    private String wokDeleteState;
}
