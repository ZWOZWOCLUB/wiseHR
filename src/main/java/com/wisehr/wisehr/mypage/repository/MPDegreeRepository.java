package com.wisehr.wisehr.mypage.repository;

import com.wisehr.wisehr.mypage.entity.MPDegree;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MPDegreeRepository extends JpaRepository<MPDegree, Integer> {

    List<MPDegree> findByMemCode(int memCode);
}
