package com.wisehr.wisehr.notice.repository;

import com.wisehr.wisehr.notice.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Integer> {

    //공지제목조회
    List<Notice> findByNotNameContaining(String search);
    //공지내용조회
    List<Notice> findByNotCommentContaining(String search);
    //공지작성자조회
    List<Notice> findByMemCode_MemNameContaining(String search);
}
