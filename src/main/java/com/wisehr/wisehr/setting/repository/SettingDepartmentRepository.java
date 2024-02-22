package com.wisehr.wisehr.setting.repository;

import com.wisehr.wisehr.setting.entity.SettingDepartment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SettingDepartmentRepository extends JpaRepository<SettingDepartment, Integer> {
    List<SettingDepartment> findByDepDeleteStatus(String N);
}
