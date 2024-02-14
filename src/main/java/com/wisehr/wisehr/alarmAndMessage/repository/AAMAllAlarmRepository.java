package com.wisehr.wisehr.alarmAndMessage.repository;

import com.wisehr.wisehr.alarmAndMessage.entity.AAMAllAlarm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AAMAllAlarmRepository extends JpaRepository<AAMAllAlarm, Integer> {

    List<AAMAllAlarm> findByMemCode(Integer memCode);
}
