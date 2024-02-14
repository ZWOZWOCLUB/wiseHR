package com.wisehr.wisehr.schedule.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Schedule {
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
    @Column(name = "wok_code")
    private int wokCode;

    public Schedule() {
    }


    public Schedule schCode(String schCode) {
        this.schCode = schCode;
        return this;
    }

    public Schedule schType(String schType) {
        this.schType = schType;
        return this;
    }

    public Schedule schStartDate(String schStartDate) {
        this.schStartDate = schStartDate;
        return this;
    }

    public Schedule schEndDate(String schEndDate) {
        this.schEndDate = schEndDate;
        return this;
    }

    public Schedule schColor(String schColor) {
        this.schColor = schColor;
        return this;
    }

    public Schedule schDeleteStatus(String schDeleteStatus) {
        this.schDeleteStatus = schDeleteStatus;
        return this;
    }

    public Schedule wokCode(int wokCode) {
        this.wokCode = wokCode;
        return this;
    }

    public Schedule build() {
        return new Schedule(schCode, schType, schStartDate, schEndDate, schColor, schDeleteStatus, wokCode);

    }
}
