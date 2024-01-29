package com.wisehr.wisehr.alarmAndMessage.repository;

import com.wisehr.wisehr.alarmAndMessage.entity.PerAlarm;
import com.wisehr.wisehr.alarmAndMessage.entity.SendMessenger;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SendMessengerRepository extends JpaRepository<SendMessenger, Integer> {

    List<SendMessenger> findByMemCode(int memCode);
}
