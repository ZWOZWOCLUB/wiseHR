package com.wisehr.wisehr.alarmAndMessage.entity;

import com.wisehr.wisehr.alarmAndMessage.dto.AAMSendMessengerDTO;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "rec_messenger")
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class AAMRecMessage {

    @Id
    @Column(name = "msg_code")
    private int msgCode;
    @OneToOne
    @JoinColumn(name = "msg_code", referencedColumnName = "msg_code")
    private AAMSendMessenger aamSendMessenger;
    @Column(name = "mem_code")
    private int memCode;

    @Override
    public String toString() {
        return "AAMRecMessage{" +
                "msgCode='" + msgCode + '\'' +
                ", aamSendMessenger=" + aamSendMessenger +
                ", memCode=" + memCode +
                '}';
    }
}
