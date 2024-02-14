package com.wisehr.wisehr.main.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@IdClass(MainSchAllowId.class)
@Table(name = "schedule_allowance")
public class MainSchAllow {

    @Id
    @Column(name = "mem_code")
    private Long memCode;

    @Id
    @Column(name = "sch_code")
    private Long schCode;

}
