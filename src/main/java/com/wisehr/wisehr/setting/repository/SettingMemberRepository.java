package com.wisehr.wisehr.setting.repository;

import com.wisehr.wisehr.setting.entity.SettingMemDepPos;
import com.wisehr.wisehr.setting.entity.SettingMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Map;

public interface SettingMemberRepository extends JpaRepository<SettingMember, Integer> {

}
