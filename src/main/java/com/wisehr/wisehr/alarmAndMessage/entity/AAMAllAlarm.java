package com.wisehr.wisehr.alarmAndMessage.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Entity
@Table(name = "all_alarm")
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AAMAllAlarm {
    @Id
    @Column(name = "all_arm_code")
    private int allArmCode;
    @Column(name = "all_arm_date")
    private String allArmDate;
    @Column(name = "all_arm_check")
    private String allArmCheck;
    @Column(name = "not_code")
    private String notCode;
    @Column(name = "mem_code")
    private int memCode;

    public AAMAllAlarm(){

    }

    public AAMAllAlarm allArmCode(int allArmCode){
        this.allArmCode = allArmCode;
        return this;
    }
    public AAMAllAlarm allArmDate(String allArmDate){
        this.allArmDate = allArmDate;
        return this;
    }
    public AAMAllAlarm allArmCheck(String allArmCheck){
        this.allArmCheck = allArmCheck;
        return this;
    }
    public AAMAllAlarm notCode(String notCode){
        this.notCode = notCode;
        return this;
    }
    public AAMAllAlarm memCode(int memCode){
        this.memCode = memCode;
        return this;
    }

    public AAMAllAlarm build(){
        return new AAMAllAlarm(allArmCode,allArmDate,allArmCheck,notCode,memCode);
    }
}
