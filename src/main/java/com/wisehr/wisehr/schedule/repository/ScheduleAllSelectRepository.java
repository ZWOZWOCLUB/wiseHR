package com.wisehr.wisehr.schedule.repository;

import com.wisehr.wisehr.schedule.entity.ScheduleAllSelect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface ScheduleAllSelectRepository extends JpaRepository<ScheduleAllSelect, String > {

    @Query("SELECT A FROM ScheduleAllSelect A " +
            "JOIN ScheduleWorkPattern B ON A.wokCode = B.wokCode " +
            "JOIN SchedulePatternDay C ON B.wokCode = C.wokCode " +
            "JOIN Schedule D ON B.wokCode = D.wokCode " +
            "JOIN ScheduleAllowance E ON A.schCode = E.schCode " +
            "JOIN SettingMember F ON E.memCode = F.memCode " +
            "Left join ScheduleEtcPattern G on F.memCode = G.memCode " +
            "WHERE :yearMonth BETWEEN FUNCTION('DATE_FORMAT', A.schStartDate, '%Y-%m') AND FUNCTION('DATE_FORMAT', A.schEndDate, '%Y-%m')")
    List<ScheduleAllSelect> findByYearMonth(String yearMonth);
}
