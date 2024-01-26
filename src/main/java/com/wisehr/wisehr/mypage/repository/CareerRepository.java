package com.wisehr.wisehr.mypage.repository;

import com.wisehr.wisehr.mypage.entity.Career;
import com.wisehr.wisehr.mypage.entity.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CareerRepository extends JpaRepository<Career, Integer> {

    List<Career> findByMemCode(int memCode);
}
