package com.wisehr.wisehr.setting.repository;

import com.wisehr.wisehr.setting.entity.SettingCareer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SettingCareerRepository extends JpaRepository<SettingCareer, String > {

    List<SettingCareer> findByMemCode(int memCode);
}
