package com.wisehr.wisehr.alarmAndMessage.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "referencer")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AAMReferencer {
    @Column(name = "mem_code")
    private int memCode;
    @Id
    @Column(name = "app_code")
    private String appCode;
}
