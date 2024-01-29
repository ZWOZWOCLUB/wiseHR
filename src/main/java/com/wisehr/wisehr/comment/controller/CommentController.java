package com.wisehr.wisehr.comment.controller;

import com.wisehr.wisehr.comment.dto.CommentDTO;
import com.wisehr.wisehr.comment.service.CommentService;
import com.wisehr.wisehr.common.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }
    @PostMapping("/comment")
    public ResponseEntity<ResponseDTO> insertComment(@RequestBody CommentDTO commentDTO){
        return null;
    }



}
