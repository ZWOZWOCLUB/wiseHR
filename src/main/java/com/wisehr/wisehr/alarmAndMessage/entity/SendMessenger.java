package com.wisehr.wisehr.alarmAndMessage.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.sql.Date;

@Entity
@Table(name = "send_messenger")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SendMessenger {

    @Id
    @Column(name = "msg_code")
    private String msgCode;
    @Column(name = "msg_date")
    private Date msgDate;
    @Column(name = "msg_contents")
    private String msgContents;
    @Column(name = "mem_code")
    private int memCode;
    @Column(name = "msg_delete_status")
    private String msgDeleteStatus;

}
