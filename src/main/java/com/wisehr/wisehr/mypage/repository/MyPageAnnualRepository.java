package com.wisehr.wisehr.mypage.repository;

import com.wisehr.wisehr.mypage.entity.MPAnnual;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyPageAnnualRepository extends JpaRepository<MPAnnual, String> {



    MPAnnual findByPayCode(String exam);
}
