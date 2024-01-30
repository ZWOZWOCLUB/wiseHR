package com.wisehr.wisehr.comment.controller;

import com.wisehr.wisehr.comment.dto.CommentDTO;
import com.wisehr.wisehr.comment.service.CommentService;
import com.wisehr.wisehr.common.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    /*댓글 등록 아직안댐*/
    @PostMapping("/comment")
    public ResponseEntity<ResponseDTO> insertComment(@RequestBody CommentDTO commentDTO){

        System.out.println("commentDTO = " + commentDTO);
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "댓글 등록 성공", commentService.insertComment(commentDTO)));
    }


    /*댓글전체조회*/
    @GetMapping("/allComment")
    public ResponseEntity<ResponseDTO> searchAllComment(
            @RequestParam(defaultValue = "all")String search){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "댓글조회성공", commentService.searchAllComment(search)));
    }

    /***
     * (공지코드)글코드로 조회
     * @param search 글코드
     * @return
     */
    @GetMapping("notCodeSearch")
    public ResponseEntity<ResponseDTO> searchNotCodeComment(
            @RequestParam(value = "nc",defaultValue = "all")String search){
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK, "글코드로 댓글조회 성공", commentService.searchNotCodeComment(search)));

    }




}
