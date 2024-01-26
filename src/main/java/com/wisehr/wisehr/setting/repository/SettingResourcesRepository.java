package com.wisehr.wisehr.setting.repository;

import com.wisehr.wisehr.setting.entity.SettingResources;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SettingResourcesRepository extends JpaRepository<SettingResources, Integer> {
    List<SettingResources> findByMemCode(int memCode);
}
