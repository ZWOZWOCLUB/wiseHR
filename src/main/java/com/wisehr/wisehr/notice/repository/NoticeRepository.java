package com.wisehr.wisehr.notice.repository;

import com.wisehr.wisehr.notice.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Integer> {
}
