package com.wisehr.wisehr.mypage.repository;

import com.wisehr.wisehr.mypage.entity.MPHoldVacation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MPHoldVacationRepository extends JpaRepository<MPHoldVacation, Integer> {
    MPHoldVacation findByMemCode(int memCode);
}
