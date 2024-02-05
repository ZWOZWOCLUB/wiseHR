package com.wisehr.wisehr.approval.entity;

import com.wisehr.wisehr.approval.dto.ApprovalWorkPatternDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Entity
@Table(name = "schedule")
public class ApprovalSchedule {
    @Id
    @Column(name = "sch_code")
    private String schCode;
    @Column(name = "sch_type")
    private String schType;
    @Column(name = "sch_start_date")
    private LocalDate schStartDate;
    @Column(name = "sch_end_date")
    private LocalDate schEndDate;
    @Column(name = "sch_color")
    private String schColor;
    @Column(name = "sch_delete_status")
    private String schDeleteStatus;
    @OneToOne
    @JoinColumn(name = "wok_code")
    private ApprovalWorkPattern workPattern;
}
