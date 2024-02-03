package com.wisehr.wisehr.alarmAndMessage.repository;

import com.wisehr.wisehr.alarmAndMessage.entity.AAMSendMessenger;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AAMSendMessengerRepository extends JpaRepository<AAMSendMessenger, Integer> {

    List<AAMSendMessenger> findByMemCode(int memCode);
}
