package com.wisehr.wisehr.attendance.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "schedule")
public class MainSchedule {

    @Id
    @Column(name = "sch_code")
    private String schCode;
    @Column(name = "sch_type")
    private String schType;
    @Column(name = "sch_start_date")
    private LocalDate schStartDate;
    @Column(name = "sch_end_date")
    private LocalDate schEndDate;
    @Column(name = "sch_delete_status")
    private String schDeleteStatus;
}
