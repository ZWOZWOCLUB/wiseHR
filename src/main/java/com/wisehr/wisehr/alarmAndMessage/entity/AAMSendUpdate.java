package com.wisehr.wisehr.alarmAndMessage.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

@Entity
@Table(name = "send_messenger")
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AAMSendUpdate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "msg_code")
    private int msgCode;
    @Column(name = "msg_date")
    private Date msgDate;
    @Column(name = "msg_contents")
    private String msgContents;
    @Column(name = "mem_code")
    private Integer memCode;
    @Column(name = "msg_delete_status")
    private String msgDeleteStatus;

    public AAMSendUpdate(){

    }

    public AAMSendUpdate msgCode(int msgCode){
        this.msgCode = msgCode;
        return this;
    }
    public AAMSendUpdate msgDate(java.sql.Date msgDate){
        this.msgDate = msgDate;
        return this;
    }
    public AAMSendUpdate msgContents(String msgContents){
        this.msgContents = msgContents;
        return this;
    }
    public AAMSendUpdate memCode(int memCode){
        this.memCode = memCode;
        return this;
    }
    public AAMSendUpdate msgDeleteStatus(String msgDeleteStatus){
        this.msgDeleteStatus = msgDeleteStatus;
        return this;
    }

    public AAMSendUpdate build(){
        return new AAMSendUpdate(msgCode,msgDate,msgContents,memCode,msgDeleteStatus);
    }

}
