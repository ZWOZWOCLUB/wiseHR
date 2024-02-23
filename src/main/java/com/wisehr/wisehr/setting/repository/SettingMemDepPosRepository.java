package com.wisehr.wisehr.setting.repository;

import com.wisehr.wisehr.setting.entity.SettingMemDepPos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SettingMemDepPosRepository extends JpaRepository <SettingMemDepPos, Integer> {
    List<SettingMemDepPos> findByMemNameContaining(String search);


}
