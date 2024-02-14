package com.wisehr.wisehr.notice.repository;

import com.wisehr.wisehr.notice.dto.NotAllAlarmDTO;
import com.wisehr.wisehr.notice.entity.NotAllAlarm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotAllAlarmRepository extends JpaRepository<NotAllAlarm, String> {
}
