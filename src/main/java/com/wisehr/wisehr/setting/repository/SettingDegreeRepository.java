package com.wisehr.wisehr.setting.repository;

import com.wisehr.wisehr.setting.entity.SettingDegree;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SettingDegreeRepository extends JpaRepository<SettingDegree, Integer> {
    List<SettingDegree> findByMemCode(int memCode);
}
