package com.wisehr.wisehr.mypage.repository;

import com.wisehr.wisehr.mypage.entity.MyPageMember;
import com.wisehr.wisehr.setting.entity.SettingMember;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MyPageRepository extends JpaRepository<MyPageMember, Integer> {

}
