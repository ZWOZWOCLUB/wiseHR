package com.wisehr.wisehr.mypage.repository;

import com.wisehr.wisehr.mypage.entity.HoldVacation;
import com.wisehr.wisehr.mypage.entity.VacationHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HoldVacationRepository extends JpaRepository<HoldVacation, Integer> {


    HoldVacation findByMemCode(int memCode);
}
