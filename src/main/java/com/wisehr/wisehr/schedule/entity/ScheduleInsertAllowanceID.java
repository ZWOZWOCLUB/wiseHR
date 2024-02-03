package com.wisehr.wisehr.schedule.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Embeddable
public class ScheduleInsertAllowanceID implements Serializable {
    private int memCode;
    private String schCode;


}
