package com.wisehr.wisehr.mypage.repository;

import com.wisehr.wisehr.mypage.entity.Annual;
import com.wisehr.wisehr.mypage.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyPageAnnualRepository extends JpaRepository<Annual, String> {



    Annual findByPayCode(String exam);
}
