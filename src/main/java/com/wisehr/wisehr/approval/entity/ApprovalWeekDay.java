package com.wisehr.wisehr.approval.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Entity
@Table(name = "week_day")
public class ApprovalWeekDay {
    @Id
    @Column(name = "day_code")
    private Long dayCode;
    @Column(name = "day_name")
    private String dayName;
}
