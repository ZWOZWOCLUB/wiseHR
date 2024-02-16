package com.wisehr.wisehr.alarmAndMessage.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.sql.Date;

@Entity
@Table(name = "per_alarm")
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AAMPerAlarm {

    @Id
    @Column(name = "per_arm_code")
    private int perArmCode;
    @Column(name = "per_arm_date_time")
    private Date perArmDateTime;
    @Column(name = "per_arm_check_status")
    private String perArmCheckStatus;
    @Column(name = "mem_code")
    private int memCode;

    public AAMPerAlarm(){

    }
    public AAMPerAlarm perArmCode(int perArmCode){
        this.perArmCode = perArmCode;
        return this;
    }
    public AAMPerAlarm perArmDateTime(Date perArmDateTime){
        this.perArmDateTime = perArmDateTime;
        return this;
    }
    public AAMPerAlarm perArmCheckStatus(String perArmCheckStatus){
        this.perArmCheckStatus = perArmCheckStatus;
        return this;
    }public AAMPerAlarm memCode(int memCode){
        this.memCode = memCode;
        return this;
    }

    public AAMPerAlarm build(){
        return new AAMPerAlarm(perArmCode,perArmDateTime,perArmCheckStatus,memCode);
    }

}
