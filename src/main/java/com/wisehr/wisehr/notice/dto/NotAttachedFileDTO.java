package com.wisehr.wisehr.notice.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class NotAttachedFileDTO {

    private Long notAtcCode;          //첨부파일코드
    private String notAtcName;        //파일이름
    private String notAtcDeleteStatus;//삭제여부
    private String notAtcPath;        //파일경로
    private NoticeDTO notice;           //글코드

    @Override
    public String toString() {
        return "NotAttachedFileDTO{" +
                "notAtcCode=" + notAtcCode +
                ", notAtcName='" + notAtcName + '\'' +
                ", notAtcDeleteStatus='" + notAtcDeleteStatus + '\'' +
                ", notAtcPath='" + notAtcPath + '\'' +
                ", notice='" + notice + '\'' +
                '}';
    }
}
