package com.wisehr.wisehr.mypage.repository;

import com.wisehr.wisehr.mypage.entity.MPMyPageMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MPMyPageRepository extends JpaRepository<MPMyPageMember, Integer> {

}
