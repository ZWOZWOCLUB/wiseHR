package com.wisehr.wisehr.comment.repository;

import com.wisehr.wisehr.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
//    List<Comment> findByNotCodeContaining(String search);

//    List<Comment> findByNotice_notCode(String search);

    List<Comment> findByNotCodeNotCode(String search);
}
