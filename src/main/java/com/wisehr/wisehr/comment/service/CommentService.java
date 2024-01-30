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

    public String  insertComment(CommentDTO commentDTO) {
        log.info("insertComment Start");
        log.info(commentDTO.toString());

        int result =0;

        try {
            Comment insertComment = modelMapper.map(commentDTO, Comment.class);
            log.info("insertComment ==========", insertComment);
            commentRepository.save(insertComment);

            result = 1;
        } catch (Exception e){
            log.info("===insertComment 오류===");
        }
        return (result>0)? "댓글등록성공": "댓글등록실패";
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

    public List<CommentDTO> searchNotCodeComment(String search) {
        log.info("searchNotCodeComment 시작");
        log.info("searchNotCodeComment : {}", search);

        List<Comment> notCodeSearchCommentValue = commentRepository.findByNotCodeContaining(search);
        List<CommentDTO> commentDTOList = notCodeSearchCommentValue.stream()
                .map(comment -> modelMapper.map(comment, CommentDTO.class))
                .collect(Collectors.toList());
        log.info("searchNotCodeComment 서비스 끝" + notCodeSearchCommentValue);
        System.out.println("notCodeSearchCommentValue = " + notCodeSearchCommentValue);
        return commentDTOList;
    }
}
