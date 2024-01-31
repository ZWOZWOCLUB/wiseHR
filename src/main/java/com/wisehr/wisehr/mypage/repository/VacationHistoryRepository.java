package com.wisehr.wisehr.mypage.repository;

import com.wisehr.wisehr.mypage.entity.Attendance;
import com.wisehr.wisehr.mypage.entity.VacationHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;

public interface VacationHistoryRepository extends JpaRepository<VacationHistory, Integer> {



}
