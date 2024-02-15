package com.wisehr.wisehr.alarmAndMessage.repository;

import com.wisehr.wisehr.alarmAndMessage.entity.AAMMessage;
import com.wisehr.wisehr.alarmAndMessage.entity.AAMRecMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AAMRecMessageRepository extends JpaRepository<AAMRecMessage, Integer> {

//    @Query("SELECT " +
//            "m, " +
//            "a " +
//            "from AAMRecMessage a " +
//            "join AAMSendMessenger m on a.msgCode = m.msgCode " +
//            "where m.msgDeleteStatus = 'N'")
    List<AAMRecMessage> findByMemCode(Integer memCode);

    List<AAMRecMessage> findByMemCodeAndRecMsgDeleteStatusOrderByMsgCodeDesc(int memCode, String n);

    List<AAMRecMessage> findTop30ByMemCodeAndRecMsgDeleteStatusOrderByMsgCodeDesc(int memCode, String n);
}
