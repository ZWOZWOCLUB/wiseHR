package com.wisehr.wisehr.mypage.repository;

import com.wisehr.wisehr.mypage.entity.MPAttendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.util.List;

public interface MPAttendanceRepository extends JpaRepository<MPAttendance, Integer> {

    MPAttendance findByAttWorkDate(Date attWorkDate);

    MPAttendance findByAttWorkDateAndMemCode(Date attWorkDate, int memCode);

    List<MPAttendance> findByMemCode(int memCode);

    List<MPAttendance> findByMemCodeAndAttWorkDateBetween(int memCode, Date date, Date date1);
}
