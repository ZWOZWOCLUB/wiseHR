package com.wisehr.wisehr.alarmAndMessage.repository;

import com.wisehr.wisehr.alarmAndMessage.entity.AAMMessage;
import com.wisehr.wisehr.alarmAndMessage.entity.AAMRecMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AAMRecMessageRepository extends JpaRepository<AAMRecMessage, Integer> {

    List<AAMRecMessage> findByMemCode(Integer memCode);
}
