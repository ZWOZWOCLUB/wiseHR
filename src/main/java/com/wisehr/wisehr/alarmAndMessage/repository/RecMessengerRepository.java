package com.wisehr.wisehr.alarmAndMessage.repository;

import com.wisehr.wisehr.alarmAndMessage.entity.RecMessenger;
import com.wisehr.wisehr.alarmAndMessage.entity.SendMessenger;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecMessengerRepository extends JpaRepository<RecMessenger, Integer> {

    List<RecMessenger> findByMemCode(int memCode);
}
