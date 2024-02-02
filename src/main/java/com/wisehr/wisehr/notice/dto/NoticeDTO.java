package com.wisehr.wisehr.notice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

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
//    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+9")
@DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date notCreateDate; //작성일
    private Date notModifyDate; //수정일
    private NotMemberDTO notMember; //작성자
    private String notDeleteStatus; //삭제여부

}
