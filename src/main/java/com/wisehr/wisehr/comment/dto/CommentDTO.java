package com.wisehr.wisehr.comment.dto;

import lombok.*;

import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CommentDTO {
    private Long comCode;   //댓글코드
    private String comContents;//내용
    private Date comDate;       //작성일시
    private Long memCode;       //댓글작성자
    private String comDeleteState;  //삭제여부
    private String notCode;     //글코드
}
