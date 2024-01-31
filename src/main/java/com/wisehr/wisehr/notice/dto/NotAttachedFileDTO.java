package com.wisehr.wisehr.notice.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class NotAttachedFileDTO {

    private Long notAtcCode;          //첨부파일코드
    private String notAtcExtens;      //확장자
    private String notAtcName;        //파일이름
    private String notAtcDeleteStatus;//삭제여부
    private String notAtcPath;        //파일경로
    private String notCode;           //글코드
}
