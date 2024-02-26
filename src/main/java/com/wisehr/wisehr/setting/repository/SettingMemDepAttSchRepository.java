package com.wisehr.wisehr.setting.repository;

import com.wisehr.wisehr.setting.entity.SettingMemDepAttSch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SettingMemDepAttSchRepository extends JpaRepository<SettingMemDepAttSch, Integer> {
    @Query(
            "SELECT A.memCode, " +
            "A.memName, " +
            "A.depCode, " +
            "B.attCode, " +
            "B.attStartTime, " +
            "B.attEndTime, " +
            "B.attStatus, " +
            "B.attWorkDate, " +
            "B.schCode, " +
            "D.schType, " +
            "D.schColor, " +
            "E.depName " +
            "from SettingMemDepAttSch A " +
            "LEFT JOIN SettingDepartment E ON A.depCode = E.depCode " +
            "join SettingAttendance B on A.memCode = B.memCode " +
            "join SettingAllowance C on A.memCode = C.allowanceID.memCode " +
            "join SettingSchedule D on B.schCode = D.schCode " +
            "where B.attWorkDate like %:yearMonth% " +
            "group by A.memCode, A.memName, A.depCode, B.attCode, B.attStartTime, B.attEndTime, B.attStatus, B.attWorkDate, B.schCode, D.schType, D.schColor " +
    "order by E.depCode")
    List<Object[]> findByYearMonth(String yearMonth);
}
