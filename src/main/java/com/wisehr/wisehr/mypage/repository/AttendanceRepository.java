package com.wisehr.wisehr.mypage.repository;

import com.wisehr.wisehr.mypage.entity.MPAttendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;

public interface AttendanceRepository extends JpaRepository<MPAttendance, Integer> {

    MPAttendance findByAttWorkDate(Date attWorkDate);

    MPAttendance findByAttWorkDateAndMemCode(Date attWorkDate, int memCode);
}
