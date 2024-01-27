package com.wisehr.wisehr.setting.repository;

import com.wisehr.wisehr.setting.entity.SettingCareer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SettingCareerRepository extends JpaRepository<SettingCareer, Integer> {
    List<SettingCareer> findByMemCode(int memCode);
}
