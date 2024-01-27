package com.wisehr.wisehr.mypage.repository;

import com.wisehr.wisehr.mypage.entity.Degree;
import com.wisehr.wisehr.mypage.entity.MyPageMember;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DegreeRepository extends JpaRepository<Degree, Integer> {

    List<Degree> findByMemCode(int memCode);
}
