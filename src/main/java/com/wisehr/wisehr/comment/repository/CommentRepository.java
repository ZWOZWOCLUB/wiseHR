package com.wisehr.wisehr.comment.repository;

import com.wisehr.wisehr.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
