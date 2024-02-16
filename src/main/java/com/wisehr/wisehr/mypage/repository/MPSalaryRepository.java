package com.wisehr.wisehr.mypage.repository;

import com.wisehr.wisehr.mypage.entity.MPDepartment;
import com.wisehr.wisehr.mypage.entity.MPSalary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MPSalaryRepository extends JpaRepository<MPSalary, String> {
    MPSalary findByMemCode(int memCode);
}
