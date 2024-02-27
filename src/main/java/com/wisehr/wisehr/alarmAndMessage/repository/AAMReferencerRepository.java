package com.wisehr.wisehr.alarmAndMessage.repository;

import com.wisehr.wisehr.alarmAndMessage.entity.AAMAllAlarm;
import com.wisehr.wisehr.alarmAndMessage.entity.AAMReferencer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AAMReferencerRepository extends JpaRepository<AAMReferencer, String> {

    List<AAMReferencer> findByMemCode(int memCode);

    List<AAMReferencer> findTop30ByMemCodeOrderByAppCodeDesc(int memCode);
}
