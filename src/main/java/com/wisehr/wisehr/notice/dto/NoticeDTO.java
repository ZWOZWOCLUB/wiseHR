package com.wisehr.wisehr.notice.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class NoticeDTO {
    private String notCode;
    private String notName;
    private String notComment;
    private String notView;
    private String notCreateDate;
    private String notModifyDate;
    private int memCode;
    private String notDeleteState;

}
