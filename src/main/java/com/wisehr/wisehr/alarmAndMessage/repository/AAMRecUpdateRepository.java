package com.wisehr.wisehr.alarmAndMessage.repository;

import com.wisehr.wisehr.alarmAndMessage.entity.AAMAllAlarm;
import com.wisehr.wisehr.alarmAndMessage.entity.AAMRecUpdate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AAMRecUpdateRepository extends JpaRepository<AAMRecUpdate, Integer> {

}
