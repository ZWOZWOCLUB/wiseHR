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
    @Column(name = "msg_code")
    private Integer msgCode;

    @Column(name="mem_code")
    private Integer memCode;

    @JoinColumn(name = "mem_code", insertable=false, updatable=false)
    @OneToOne
    private AAMMember aamMember;
//    @JoinColumn(name = "mem_code", referencedColumnName = "mem_code")
//    @OneToOne
//    private AAMMember aamMember;

    @Override
    public String toString() {
        return "AAMRecMessenger{" +
                "msgCode='" + msgCode + '\'' +
                '}';
    }
}
