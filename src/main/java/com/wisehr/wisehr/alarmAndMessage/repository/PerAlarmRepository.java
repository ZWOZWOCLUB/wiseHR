package com.wisehr.wisehr.alarmAndMessage.repository;

import com.wisehr.wisehr.alarmAndMessage.entity.PerAlarm;
import com.wisehr.wisehr.mypage.entity.Career;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PerAlarmRepository extends JpaRepository<PerAlarm, Integer> {

    List<PerAlarm> findByMemCode(int memCode);
}
