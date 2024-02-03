package com.wisehr.wisehr.schedule.repository;

import com.wisehr.wisehr.schedule.entity.ScheduleAllSelect;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface ScheduleAllSelectRepository extends JpaRepository<ScheduleAllSelect, String > {
    @EntityGraph(attributePaths = {"patternList"}, type = EntityGraph.EntityGraphType.FETCH)
    @Query("SELECT A FROM ScheduleAllSelect A " +
            "LEFT JOIN fetch ScheduleWorkPattern B ON A.wokCode = B.wokCode " +
            "LEFT JOIN fetch SchedulePatternDay C ON A.wokCode = C.patternDayID.wokCode " +
            "LEFT JOIN fetch ScheduleAllowance E ON A.schCode = E.allowanceID.schCode " +
            "LEFT JOIN fetch ScheduleMember F ON E.allowanceID.memCode = F.memCode " +
            "LEFT JOIN fetch ScheduleEtcPattern G on F.memCode = G.memCode " +
            "WHERE :yearMonth BETWEEN FUNCTION('DATE_FORMAT', A.schStartDate, '%Y-%m') AND FUNCTION('DATE_FORMAT', A.schEndDate, '%Y-%m')")
    List<ScheduleAllSelect> findByYearMonth(String yearMonth);
    @EntityGraph(attributePaths = {"patternList"}, type = EntityGraph.EntityGraphType.FETCH)
    @Query("SELECT A FROM ScheduleAllSelect A " +
            "LEFT JOIN fetch ScheduleWorkPattern B ON A.wokCode = B.wokCode " +
            "LEFT JOIN fetch SchedulePatternDay C ON A.wokCode = C.patternDayID.wokCode " +
            "LEFT JOIN fetch ScheduleAllowance E ON A.schCode = E.allowanceID.schCode " +
            "LEFT JOIN fetch ScheduleMember F ON E.allowanceID.memCode = F.memCode " +
            "LEFT JOIN fetch ScheduleEtcPattern G on F.memCode = G.memCode " +
            "WHERE :yearMonth BETWEEN FUNCTION('DATE_FORMAT', A.schStartDate, '%Y-%m') AND FUNCTION('DATE_FORMAT', A.schEndDate, '%Y-%m') " +
            "and E.allowanceID.memCode = :memCode ")
    List<ScheduleAllSelect> findByYearMonthAndMemCode(String yearMonth, int memCode);
    @EntityGraph(attributePaths = {"patternList"}, type = EntityGraph.EntityGraphType.FETCH)
    @Query("SELECT A FROM ScheduleAllSelect A " +
            "LEFT JOIN fetch ScheduleWorkPattern B ON A.wokCode = B.wokCode " +
            "LEFT JOIN fetch SchedulePatternDay C ON A.wokCode = C.patternDayID.wokCode " +
            "LEFT JOIN fetch ScheduleAllowance E ON A.schCode = E.allowanceID.schCode " +
            "LEFT JOIN fetch ScheduleMember F ON E.allowanceID.memCode = F.memCode " +
            "LEFT JOIN fetch ScheduleEtcPattern G on F.memCode = G.memCode " +
            "WHERE :yearMonth BETWEEN FUNCTION('DATE_FORMAT', A.schStartDate, '%Y-%m') AND FUNCTION('DATE_FORMAT', A.schEndDate, '%Y-%m') " +
            "and F.memName = :memName ")
    List<ScheduleAllSelect> findByYearMonthAndMemName(String yearMonth, String memName);
    @EntityGraph(attributePaths = {"patternList"}, type = EntityGraph.EntityGraphType.FETCH)
    @Query("SELECT A FROM ScheduleAllSelect A " +
            "LEFT JOIN fetch ScheduleWorkPattern B ON A.wokCode = B.wokCode " +
            "LEFT JOIN fetch SchedulePatternDay C ON A.wokCode = C.patternDayID.wokCode " +
            "LEFT JOIN fetch ScheduleAllowance E ON A.schCode = E.allowanceID.schCode " +
            "LEFT JOIN fetch ScheduleMember F ON E.allowanceID.memCode = F.memCode " +
            "LEFT JOIN fetch ScheduleEtcPattern G on F.memCode = G.memCode " +
            "WHERE :yearMonth BETWEEN FUNCTION('DATE_FORMAT', A.schStartDate, '%Y-%m') AND FUNCTION('DATE_FORMAT', A.schEndDate, '%Y-%m') " +
            "and F.depCode.depCode = :depCode ")
    List<ScheduleAllSelect> findByYearMonthAndDepCode(String yearMonth, int depCode);

    @EntityGraph(attributePaths = {"patternList"}, type = EntityGraph.EntityGraphType.FETCH)
    @Query("SELECT A FROM ScheduleAllSelect A " +
            "LEFT JOIN fetch ScheduleWorkPattern B ON A.wokCode = B.wokCode " +
            "LEFT JOIN fetch SchedulePatternDay C ON A.wokCode = C.patternDayID.wokCode " +
            "LEFT JOIN fetch ScheduleAllowance E ON A.schCode = E.allowanceID.schCode " +
            "LEFT JOIN fetch ScheduleMember F ON E.allowanceID.memCode = F.memCode " +
            "LEFT JOIN fetch ScheduleEtcPattern G on F.memCode = G.memCode " +
            "WHERE :yearMonth BETWEEN FUNCTION('DATE_FORMAT', A.schStartDate, '%Y-%m') AND FUNCTION('DATE_FORMAT', A.schEndDate, '%Y-%m') " +
            "and F.depCode.depName LIKE %:depName% ")
    List<ScheduleAllSelect> findByYearMonthAndDepName(String yearMonth, String depName);
    @EntityGraph(attributePaths = {"patternList"}, type = EntityGraph.EntityGraphType.FETCH)
    @Query("SELECT A FROM ScheduleAllSelect A " +
            "LEFT JOIN fetch ScheduleWorkPattern B ON A.wokCode = B.wokCode " +
            "LEFT JOIN fetch SchedulePatternDay C ON A.wokCode = C.patternDayID.wokCode " +
            "LEFT JOIN fetch ScheduleAllowance E ON A.schCode = E.allowanceID.schCode " +
            "LEFT JOIN fetch ScheduleMember F ON E.allowanceID.memCode = F.memCode " +
            "LEFT JOIN fetch ScheduleEtcPattern G on F.memCode = G.memCode " +
            "WHERE :yearMonth BETWEEN FUNCTION('DATE_FORMAT', A.schStartDate, '%Y-%m-%') AND FUNCTION('DATE_FORMAT', A.schEndDate, '%Y-%m-%d') ")
    List<ScheduleAllSelect> findByYearMonthDate(String yearMonth);
}
