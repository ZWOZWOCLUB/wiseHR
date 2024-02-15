package com.wisehr.wisehr.alarmAndMessage.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "rec_messenger")
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AAMRecUpdate {

    @Id
    @Column(name = "msg_code")
    private Integer msgCode;
    @Column(name="mem_code")
    private Integer memCode;
    @Column(name = "rec_msg_delete_status")
    private String recMsgDeleteStatus;
    @Column(name = "rec_msg_check_status")
    private String recMsgCheckStatus;

    public AAMRecUpdate(){
    }

    public AAMRecUpdate msgCode(int msgCode){
        this.msgCode = msgCode;
        return this;
    }
    public AAMRecUpdate memCode(int memCode){
        this.memCode = memCode;
        return this;
    }
    public AAMRecUpdate recMsgDeleteStatus(String recMsgDeleteStatus){
        this.recMsgDeleteStatus = recMsgDeleteStatus;
        return this;
    }
    public AAMRecUpdate recMsgCheckStatus(String recMsgCheckStatus){
        this.recMsgCheckStatus = recMsgCheckStatus;
        return this;
    }

    public AAMRecUpdate build(){
        return new AAMRecUpdate(msgCode,memCode,recMsgDeleteStatus,recMsgCheckStatus);
    }
}
