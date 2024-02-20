package com.wisehr.wisehr.schedule.repository;

import com.wisehr.wisehr.schedule.entity.ScheduleAllSelect;
import com.wisehr.wisehr.schedule.entity.ScheduleMemSch;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ScheduleMemSchRepository extends JpaRepository <ScheduleMemSch, Integer> {
    @Query("SELECT A FROM ScheduleMemSch A " +
            "LEFT JOIN ScheduleAllowance B ON A.memCode = B.allowanceID.memCode " +
            "LEFT JOIN Schedule C ON B.allowanceID.schCode = C.schCode " +
            "WHERE B.allowanceID.schCode IS NUll OR C.schDeleteStatus = 'Y'" +
            "AND A.memStatus= 'N'" +
            "OR NOT (:notContain BETWEEN C.schStartDate AND C.schEndDate)" +
            "order by A.deplist.depCode")
    List<ScheduleMemSch> findByYearMonthNotContain(String notContain);
}
