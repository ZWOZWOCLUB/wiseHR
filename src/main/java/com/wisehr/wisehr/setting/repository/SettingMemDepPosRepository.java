package com.wisehr.wisehr.setting.repository;

import com.wisehr.wisehr.setting.entity.SettingMemDepPos;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SettingMemDepPosRepository extends JpaRepository <SettingMemDepPos, Integer> {
    List<SettingMemDepPos> findByMemNameContainingAndMemStatus(String search, String memStatus);


}
