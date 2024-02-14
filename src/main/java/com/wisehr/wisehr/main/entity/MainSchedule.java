package com.wisehr.wisehr.main.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "schedule")
public class MainSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sch_code")
    private String schCode;
    @Column(name = "sch_type")
    private String schType;
    @Column(name = "sch_start_date")
    private LocalDate startDate;
    @Column(name = "sch_end_date")
    private LocalDate endDate;
    @Column(name = "sch_color")
    private String color;
    @Column(name = "sch_delete_staus")
    private String deleteStatus;

    @OneToMany(mappedBy = "schCode")
    private List<MainSchAllow> mainSch;

}
