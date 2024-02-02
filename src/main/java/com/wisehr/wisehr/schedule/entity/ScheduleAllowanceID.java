package com.wisehr.wisehr.schedule.entity;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ScheduleAllowanceID implements Serializable {
    private int memCode;
    private String schCode;
}
