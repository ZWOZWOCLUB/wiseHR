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
            "LEFT JOIN ScheduleWorkPattern B ON A.wokCode = B.wokCode " +
            "JOIN SchedulePatternDay C ON A.wokCode = C.patternDayID.wokCode " +
            "LEFT JOIN ScheduleAllowance E ON A.schCode = E.allowanceID.schCode " +
            "LEFT JOIN ScheduleMember F ON E.allowanceID.memCode = F.memCode " +
            "WHERE (:yearMonth BETWEEN DATE_FORMAT(A.schStartDate, '%Y-%m') AND DATE_FORMAT(A.schEndDate, '%Y-%m')) " +
            "AND A.schDeleteStatus = 'N'")
    List<ScheduleAllSelect> findByYearMonth(String yearMonth);

    @EntityGraph(attributePaths = {"patternList"}, type = EntityGraph.EntityGraphType.FETCH)
    @Query("SELECT A FROM ScheduleAllSelect A " +
            "LEFT JOIN ScheduleWorkPattern B ON A.wokCode = B.wokCode " +
            "JOIN SchedulePatternDay C ON A.wokCode = C.patternDayID.wokCode " +
            "LEFT JOIN ScheduleAllowance E ON A.schCode = E.allowanceID.schCode " +
            "LEFT JOIN ScheduleMember F ON E.allowanceID.memCode = F.memCode " +
            "WHERE (:memCode = 0 OR E.allowanceID.memCode = :memCode)" +
            "AND A.schDeleteStatus = 'N'")
    List<ScheduleAllSelect> findByYearMonth(int memCode);


    @EntityGraph(attributePaths = {"patternList"}, type = EntityGraph.EntityGraphType.FETCH)
    @Query("SELECT A FROM ScheduleAllSelect A " +
            "LEFT JOIN fetch ScheduleWorkPattern B ON A.wokCode = B.wokCode " +
            "LEFT JOIN fetch SchedulePatternDay C ON A.wokCode = C.patternDayID.wokCode " +
            "LEFT JOIN fetch ScheduleAllowance E ON A.schCode = E.allowanceID.schCode " +
            "LEFT JOIN fetch ScheduleMember F ON E.allowanceID.memCode = F.memCode " +
            "LEFT JOIN fetch ScheduleEtcPattern G on F.memCode = G.memCode " +
            "WHERE (:yearMonth is null OR :yearMonth BETWEEN FUNCTION('DATE_FORMAT', A.schStartDate, '%Y-%m-%d') AND FUNCTION('DATE_FORMAT', A.schEndDate, '%Y-%m-%d')) " +
            "AND (:notContain is null OR :notContain BETWEEN FUNCTION('DATE_FORMAT', A.schStartDate, '%Y-%m-%d') AND FUNCTION('DATE_FORMAT', A.schEndDate, '%Y-%m-%d')) " +
            "AND A.schDeleteStatus = 'N'" +
            "order by A.schEndDate DESC")
    List<ScheduleAllSelect> findByYearMonthDate(String yearMonth, String notContain);

    @EntityGraph(attributePaths = {"patternList"}, type = EntityGraph.EntityGraphType.FETCH)
    @Query("SELECT A FROM ScheduleAllSelect A " +
            "LEFT JOIN fetch ScheduleWorkPattern B ON A.wokCode = B.wokCode " +
            "LEFT JOIN fetch SchedulePatternDay C ON A.wokCode = C.patternDayID.wokCode " +
            "WHERE A.schDeleteStatus = 'N'" +
            "order by A.schEndDate DESC")
        List<ScheduleAllSelect> findByAll();


    @EntityGraph(attributePaths = {"patternList"}, type = EntityGraph.EntityGraphType.FETCH)
    @Query("SELECT A FROM ScheduleAllSelect A " +
            "LEFT JOIN ScheduleWorkPattern B ON A.wokCode = B.wokCode " +
            "JOIN SchedulePatternDay C ON A.wokCode = C.patternDayID.wokCode " +
            "LEFT JOIN ScheduleAllowance E ON A.schCode = E.allowanceID.schCode " +
            "LEFT JOIN ScheduleMember F ON E.allowanceID.memCode = F.memCode " +
            "WHERE (:yearMonth BETWEEN DATE_FORMAT(A.schStartDate, '%Y-%m') AND DATE_FORMAT(A.schEndDate, '%Y-%m')) " +
            "AND (F.memCode IN (:memCode)) " +
            "AND A.schDeleteStatus = 'N'")
    List<ScheduleAllSelect> findByYearMonthAndMemCode(List<String >memCode, String yearMonth);
}
