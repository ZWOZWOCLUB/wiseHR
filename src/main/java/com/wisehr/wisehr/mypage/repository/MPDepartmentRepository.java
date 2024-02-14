package com.wisehr.wisehr.mypage.repository;

import com.wisehr.wisehr.mypage.entity.MPDepartment;
import com.wisehr.wisehr.setting.entity.SettingDepartment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MPDepartmentRepository extends JpaRepository<MPDepartment, Integer> {
    MPDepartment findByDepCode(int depCode);
}
