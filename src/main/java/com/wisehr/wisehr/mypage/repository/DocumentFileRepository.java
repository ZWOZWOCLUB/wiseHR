package com.wisehr.wisehr.mypage.repository;

import com.wisehr.wisehr.mypage.entity.DocumentFile;
import com.wisehr.wisehr.mypage.entity.MyPageMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentFileRepository extends JpaRepository<DocumentFile, Integer> {

    List<DocumentFile> findByMemCode(int memCode);
}
