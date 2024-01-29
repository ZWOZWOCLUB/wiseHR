package com.wisehr.wisehr.notice.repository;

import com.wisehr.wisehr.notice.entity.NotComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<NotComment, Integer> {
}
