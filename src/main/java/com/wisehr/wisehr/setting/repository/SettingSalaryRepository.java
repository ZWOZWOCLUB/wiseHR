package com.wisehr.wisehr.setting.repository;

import com.wisehr.wisehr.setting.entity.SettingSalary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SettingSalaryRepository extends JpaRepository<SettingSalary, String > {
    SettingSalary findByMemCode(int memCode);
}
