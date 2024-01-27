package com.wisehr.wisehr.alarmAndMessage.repository;

import com.wisehr.wisehr.alarmAndMessage.entity.AllAlarm;
import com.wisehr.wisehr.alarmAndMessage.entity.PerAlarm;
import com.wisehr.wisehr.mypage.entity.Career;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AllAlarmRepository extends JpaRepository<AllAlarm, Integer> {

    List<AllAlarm> findByMemCode(int memCode);
}
