package com.wisehr.wisehr.mypage.repository;

import com.wisehr.wisehr.mypage.entity.MPPosition;
import com.wisehr.wisehr.setting.entity.SettingPosition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MPPositionRepository extends JpaRepository<MPPosition, Integer> {
    MPPosition findByPosCode(int posCode);
}
