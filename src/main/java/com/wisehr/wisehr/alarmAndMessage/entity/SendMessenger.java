package com.wisehr.wisehr.alarmAndMessage.entity;

import jakarta.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "msg_code")
    private String msgCode;
    @Column(name = "msg_date")
    private Date msgDate;
    @Column(name = "msg_contents")
    private String msgContents;
    @Column(name = "mem_code")
    private Long memCode;
    @Column(name = "msg_delete_status")
    private String msgDeleteStatus;

}
