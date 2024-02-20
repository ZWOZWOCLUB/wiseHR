package com.wisehr.wisehr.alarmAndMessage.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "rec_messenger")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AAMRecMessenger {

    @Id
    @Column(name = "rec_msg_code")
    private Integer recMsgCode;

    @Column(name = "msg_code")
    private Integer msgCode;

    @Column(name="mem_code")
    private Integer memCode;

    @Column(name = "rec_msg_delete_status")
    private String recMsgDeleteStatus;
    @Column(name = "rec_msg_check_status")
    private String recMsgCheckStatus;
    @JoinColumn(name = "mem_code", insertable=false, updatable=false)
    @OneToOne
    private AAMMember aamMember;
//    @JoinColumn(name = "mem_code", referencedColumnName = "mem_code")
//    @OneToOne
//    private AAMMember aamMember;


    @Override
    public String toString() {
        return "AAMRecMessenger{" +
                "msgCode=" + msgCode +
                ", recMsgDeleteStatus='" + recMsgDeleteStatus + '\'' +
                ", recMsgCheckStatus='" + recMsgCheckStatus + '\'' +
                '}';
    }
}
