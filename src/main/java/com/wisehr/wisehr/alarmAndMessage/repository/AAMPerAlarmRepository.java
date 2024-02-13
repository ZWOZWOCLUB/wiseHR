package com.wisehr.wisehr.alarmAndMessage.repository;

import com.wisehr.wisehr.alarmAndMessage.entity.AAMPerAlarm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AAMPerAlarmRepository extends JpaRepository<AAMPerAlarm, Integer> {

    List<AAMPerAlarm> findByMemCode(int memCode);

    List<AAMPerAlarm> findByMemCodeOrderByPerArmCodeDesc(int memCode);
}
