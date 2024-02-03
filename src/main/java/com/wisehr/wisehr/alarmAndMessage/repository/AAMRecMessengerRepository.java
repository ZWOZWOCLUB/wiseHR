package com.wisehr.wisehr.alarmAndMessage.repository;

import com.wisehr.wisehr.alarmAndMessage.entity.AAMRecMessenger;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AAMRecMessengerRepository extends JpaRepository<AAMRecMessenger, Integer> {

    List<AAMRecMessenger> findByMemCode(int memCode);
}
