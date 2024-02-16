package com.wisehr.wisehr.alarmAndMessage.repository;

import com.wisehr.wisehr.alarmAndMessage.entity.AAMRecUpdate;
import com.wisehr.wisehr.alarmAndMessage.entity.AAMSendUpdate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AAMSendUpdateRepository extends JpaRepository<AAMSendUpdate, Integer> {

}
