package com.wisehr.wisehr.schedule.entity;

import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name = "schedule_allowance")
@IdClass(ScheduleAllowanceID.class)
@AllArgsConstructor
@Getter
@ToString
public class ScheduleAllowance {
    @Id
    @Column(name = "mem_code")
    private int memCode;
    @Id
    @Column(name = "sch_code")
    private String schCode;

    public ScheduleAllowance() {
    }



        public ScheduleAllowance memCode(int memCode) {
            this.memCode = memCode;
            return this;
        }

        public ScheduleAllowance schCode(String schCode) {
            this.schCode = schCode;
            return this;
        }

        public ScheduleAllowance build() {
            return new ScheduleAllowance(memCode, schCode);
        }

}
