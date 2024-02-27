package com.wisehr.wisehr.comment.service;

import com.wisehr.wisehr.comment.dto.CommentDTO;
import com.wisehr.wisehr.comment.entity.Comment;
import com.wisehr.wisehr.comment.repository.CommentRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CommentService {

    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;

    public CommentService(CommentRepository commentRepository, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.modelMapper = modelMapper;
    }

    //댓글등록
    public CommentDTO  insertComment(CommentDTO commentDTO) {
        log.info("insertComment Start");

        int result =0;

        try {
            Comment insertComment = modelMapper.map(commentDTO, Comment.class);
            log.info("insertComment ==========", insertComment);
            System.out.println("wwwwwwwww");
            Comment comment = commentRepository.save(insertComment);
        log.info(comment.toString());
        CommentDTO saveCommentDTO = modelMapper.map(comment, CommentDTO.class);

        return saveCommentDTO;
//            result = 1;
        } catch (Exception e){
            log.info("===insertComment 오류===");
            throw new RuntimeException("댓글등록실패");
        }
//        return (result>0)? "댓글등록성공": "댓글등록실패";
    }


    public List<CommentDTO> searchAllComment(String search) {
        log.info("searchAllComment시작");
        log.info("searchAllComment : {}", search);

        List<Comment> commentValue = commentRepository.findAll();
        List<CommentDTO> commentDTOList = commentValue.stream()
                .map(comment -> modelMapper.map(comment, CommentDTO.class))
                .collect(Collectors.toList());
        log.info("searchAllComment 끝" + commentValue);
        System.out.println(commentValue);

        return commentDTOList;
    }

    //댓글조회
//    public List<CommentDTO> searchNotCodeComment(String search) {
//        log.info("searchNotCodeComment 시작");
//        log.info("searchNotCodeComment : {}", search);
//
//        // 데이터베이스에서 검색어에 해당하는 댓글 조회
//        List<Comment> notCodeSearchCommentValue = commentRepository.findByNotCodeNotCode(search);
//
//        // comDeleteState가 "Y"인 댓글만 필터링하여 DTO로 변환
//        List<CommentDTO> commentDTOList = notCodeSearchCommentValue.stream()
//                .filter(comment -> "Y".equals(comment.getComDeleteState())) // comDeleteState 값이 "Y"인 항목만 필터링
//                .map(comment -> modelMapper.map(comment, CommentDTO.class)) // Comment 객체를 CommentDTO 객체로 매핑
//                .collect(Collectors.toList());
//
//        log.info("searchNotCodeComment 서비스 끝" + notCodeSearchCommentValue);
//        System.out.println("notCodeSearchCommentValue = " + notCodeSearchCommentValue);
//        return commentDTOList;
//    }
    public List<CommentDTO> searchNotCodeComment(String search) {
        log.info("searchNotCodeComment 시작");
        log.info("searchNotCodeComment : {}", search);

        List<Comment> notCodeSearchCommentValue = commentRepository.findByNotCodeNotCodeAndComDeleteState(search,"N");
        List<CommentDTO> commentDTOList = notCodeSearchCommentValue.stream()
                .map(comment -> modelMapper.map(comment, CommentDTO.class))
                .collect(Collectors.toList());
        log.info("searchNotCodeComment 서비스 끝" + notCodeSearchCommentValue);
        System.out.println("notCodeSearchCommentValue = " + notCodeSearchCommentValue);
        return commentDTOList;
    }

    //댓글 삭제처리
    public String commentDelete(String comCode) {

        int result = 0 ;

        Long code = Long.parseLong(comCode);

        Comment deleteComment = commentRepository.findByComCode(code);

        log.info("deleteComment info : " + deleteComment );
        try {
            deleteComment.setComDeleteState("Y");

            commentRepository.save(deleteComment);
            result = 1 ;
        }catch (Exception e){
            e.printStackTrace();
        }


        return (result > 0) ? "성공" : "실패";


    }
}
