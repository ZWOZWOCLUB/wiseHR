package com.wisehr.wisehr.notice.dto;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class NoticeDTO {
    private String notCode;
    private String notName;
    private String notComment;  //내용
    private Long notView;       //조회수
    private Date notCreateDate; //작성일
    private Date notModifyDate; //수정일
    private NotMemberDTO notMember; //작성자
    private String notDeleteStatus; //삭제여부

}
