package com.wisehr.wisehr.notice.repository;

import com.wisehr.wisehr.notice.entity.NoticeResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeResRepository extends JpaRepository<NoticeResponse, String> {
    //공지제목조회
    List<NoticeResponse> findByNotNameContaining(String search);
    //공지내용조회
    List<NoticeResponse> findByNotCommentContaining(String search);
    //공지작성자조회
    List<NoticeResponse> findByNotMemberMemNameContaining(String search);

    List<NoticeResponse> findByNotCode(String search);
}
