package com.wisehr.wisehr.setting.repository;

import com.wisehr.wisehr.setting.entity.SettingCareerFile;
import com.wisehr.wisehr.setting.entity.SettingCertificate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SettingCareerFileRepository extends JpaRepository<SettingCareerFile, Integer> {
    List<SettingCareerFile> findByCrrCode(String crrCode);
}
