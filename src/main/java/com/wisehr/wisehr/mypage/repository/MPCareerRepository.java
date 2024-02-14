package com.wisehr.wisehr.mypage.repository;

import com.wisehr.wisehr.mypage.entity.MPCareer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MPCareerRepository extends JpaRepository<MPCareer, Integer> {

    List<MPCareer> findByMemCode(int memCode);
}
