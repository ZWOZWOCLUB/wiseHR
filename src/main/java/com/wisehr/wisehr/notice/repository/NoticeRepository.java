package com.wisehr.wisehr.notice.repository;

import com.wisehr.wisehr.notice.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Integer> {

    List<Notice> findByNotNameContaining(String search);

    List<Notice> findByNotCommentContaining(String search);
}
