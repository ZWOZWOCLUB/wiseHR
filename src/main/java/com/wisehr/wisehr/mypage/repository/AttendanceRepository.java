package com.wisehr.wisehr.mypage.repository;

import com.wisehr.wisehr.mypage.entity.Attendance;
import com.wisehr.wisehr.mypage.entity.Career;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {

    Attendance findByAttWorkDate(Date attWorkDate);

    Attendance findByAttWorkDateAndMemCode(Date attWorkDate, int memCode);
}
