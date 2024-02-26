package com.wisehr.wisehr.schedule.repository;

import com.wisehr.wisehr.schedule.entity.ScheduleWorkPattern;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ScheduleWorkPatternRepository extends JpaRepository<ScheduleWorkPattern, Integer> {

@Query("select A " +
        "from ScheduleWorkPattern A " +
        "left join SchedulePatternDay B on A.wokCode = B.patternDayID.wokCode " +
        "where B.patternDayID.wokCode is null " +
"AND A.wokDeleteState = :N " )
    List<ScheduleWorkPattern> findByWokDeleteState(String N);
}
