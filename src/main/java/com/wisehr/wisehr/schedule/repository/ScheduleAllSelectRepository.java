package com.wisehr.wisehr.schedule.repository;

import com.wisehr.wisehr.schedule.entity.ScheduleAllSelect;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ScheduleAllSelectRepository extends JpaRepository<ScheduleAllSelect, String > {
    @EntityGraph(attributePaths = {"patternList"}, type = EntityGraph.EntityGraphType.FETCH)
    @Query("SELECT A FROM ScheduleAllSelect A " +
            "LEFT JOIN fetch ScheduleWorkPattern B ON A.wokCode = B.wokCode " +
            "LEFT JOIN fetch SchedulePatternDay C ON A.wokCode = C.patternDayID.wokCode " +
            "LEFT JOIN fetch ScheduleAllowance E ON A.schCode = E.allowanceID.schCode " +
            "LEFT JOIN fetch ScheduleMember F ON E.allowanceID.memCode = F.memCode " +
            "LEFT JOIN fetch ScheduleEtcPattern G on F.memCode = G.memCode " +
            "WHERE (:yearMonth is null OR :yearMonth BETWEEN FUNCTION('DATE_FORMAT', A.schStartDate, '%Y-%m') AND FUNCTION('DATE_FORMAT', A.schEndDate, '%Y-%m')) " +
            "AND (:memCode = 0 OR E.allowanceID.memCode = :memCode) " +
            "AND (:memName is null OR F.memName = :memName) " +
            "AND (:depCode = 0 OR F.depCode.depCode = :depCode) " +
            "AND (:depName is null OR F.depCode.depName LIKE %:depName%)")
    List<ScheduleAllSelect> findByYearMonth(int memCode, String memName, int depCode, String depName, String yearMonth);

    @EntityGraph(attributePaths = {"patternList"}, type = EntityGraph.EntityGraphType.FETCH)
    @Query("SELECT A FROM ScheduleAllSelect A " +
            "LEFT JOIN fetch ScheduleWorkPattern B ON A.wokCode = B.wokCode " +
            "LEFT JOIN fetch SchedulePatternDay C ON A.wokCode = C.patternDayID.wokCode " +
            "LEFT JOIN fetch ScheduleAllowance E ON A.schCode = E.allowanceID.schCode " +
            "LEFT JOIN fetch ScheduleMember F ON E.allowanceID.memCode = F.memCode " +
            "LEFT JOIN fetch ScheduleEtcPattern G on F.memCode = G.memCode " +
            "WHERE (:yearMonth is null OR :yearMonth BETWEEN FUNCTION('DATE_FORMAT', A.schStartDate, '%Y-%m-%d') AND FUNCTION('DATE_FORMAT', A.schEndDate, '%Y-%m-%d')) " +
            "AND (:notContain is null OR :notContain BETWEEN FUNCTION('DATE_FORMAT', A.schStartDate, '%Y-%m-%d') AND FUNCTION('DATE_FORMAT', A.schEndDate, '%Y-%m-%d')) ")
    List<ScheduleAllSelect> findByYearMonthDate(String yearMonth, String notContain);

    @EntityGraph(attributePaths = {"patternList"}, type = EntityGraph.EntityGraphType.FETCH)
    @Query("SELECT A FROM ScheduleAllSelect A " +
            "LEFT JOIN fetch ScheduleWorkPattern B ON A.wokCode = B.wokCode " +
            "LEFT JOIN fetch SchedulePatternDay C ON A.wokCode = C.patternDayID.wokCode ")
    List<ScheduleAllSelect> findByAll();

}
