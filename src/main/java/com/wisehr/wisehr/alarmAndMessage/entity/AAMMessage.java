package com.wisehr.wisehr.alarmAndMessage.entity;

import com.wisehr.wisehr.alarmAndMessage.dto.AAMRecMessengerDTO;
import com.wisehr.wisehr.mypage.entity.MPDocumentFile;
import com.wisehr.wisehr.mypage.entity.MPSalary;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.util.List;

@Table(name="send_messenger")
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class AAMMessage {
    @Id
    @Column(name="msg_code")
    private Integer msgCode;

    @JoinColumn(name = "msg_code")
    @OneToMany
    private List<AAMRecMessenger> aamRecMessenger;
    @Column(name = "msg_date")
    private String msgDate;
    @Column(name = "msg_contents")
    private String msgContents;
    @Column(name = "mem_code")
    private Long memCode;
    @Column(name = "msg_delete_status")
    private String msgDeleteStatus;


    @Override
    public String toString() {
        return "AAMMessage{" +
                "msgCode=" + msgCode +
                ", msgDate=" + msgDate +
                ", msgContents='" + msgContents + '\'' +
                ", memCode=" + memCode +
                ", msgDeleteStatus='" + msgDeleteStatus + '\'' +
                '}';
    }
}



