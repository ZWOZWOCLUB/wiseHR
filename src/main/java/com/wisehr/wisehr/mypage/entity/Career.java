package com.wisehr.wisehr.mypage.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.sql.Date;

@Entity
@Table(name = "career")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Career {
    @Id
    @Column(name = "crr_code")
    private String crrCode;
    @Column(name = "crr_name")
    private String crrName;
    @Column(name = "crr_position")
    private String crrPosition;
    @Column(name = "crr_start_date")
    private Date crrStartDate;
    @Column(name = "crr_end_date")
    private Date crrEndDate;
    @Column(name = "crr_state")
    private String crrState;
    @Column(name = "crr_description")
    private String crrDescription;
    @Column(name = "mem_code")
    private int memCode;
}
