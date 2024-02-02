package com.wisehr.wisehr.alarmAndMessage.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "rec_messenger")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AAMRecMessenger {

    @Id
    @Column(name = "msg_code")
    private String msgCode;
    @Column(name = "mem_code")
    private int memCode;

}
