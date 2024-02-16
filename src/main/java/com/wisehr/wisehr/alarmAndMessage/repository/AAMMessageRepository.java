package com.wisehr.wisehr.alarmAndMessage.repository;

import com.wisehr.wisehr.alarmAndMessage.entity.AAMAllAlarm;
import com.wisehr.wisehr.alarmAndMessage.entity.AAMMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AAMMessageRepository extends JpaRepository<AAMMessage, Integer> {

    List<AAMMessage> findByMemCode(Integer memCode);

    List<AAMMessage> findByMemCodeAndMsgDeleteStatusOrderByMsgCodeDesc(int memCode, String n);

    List<AAMMessage> findTop30ByMemCodeAndMsgDeleteStatusOrderByMsgCodeDesc(int memCode, String n);
}
