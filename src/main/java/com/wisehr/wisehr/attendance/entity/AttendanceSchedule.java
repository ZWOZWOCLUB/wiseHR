package com.wisehr.wisehr.attendance.entity;

import com.wisehr.wisehr.attendance.dto.AttendanceWorkPatternDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
@Table(name = "schedule")
public class AttendanceSchedule {
    @Id
    @GeneratedValue(generator = "eegenerator")
    @GenericGenerator(name = "eegenerator",
            parameters = @org.hibernate.annotations.Parameter(name = "prefix", value = "sch"),
            strategy = "com.wisehr.wisehr.common.MyGenerator")
    @Column(name = "sch_code")
    private String schCode;
    @Column(name = "sch_type")
    private String schType;
    @Column(name = "sch_start_date")
    private String schStartDate;
    @Column(name = "sch_end_date")
    private String schEndDate;
    @Column(name = "sch_color")
    private String schColor;
    @Column(name = "sch_delete_status")
    private String schDeleteStatus;
    @ManyToOne
    @JoinColumn(name = "wok_code")
    private AttendanceWorkPattern workPattern;
}
