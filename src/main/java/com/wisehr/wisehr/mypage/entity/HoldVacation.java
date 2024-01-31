package com.wisehr.wisehr.mypage.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "hold_vacation")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class HoldVacation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mem_code")
    private long memCode;
    @Column(name = "vct_count")
    private int vctCount;
    @Column(name = "vct_deadline")
    private int vctDeadline;
}
