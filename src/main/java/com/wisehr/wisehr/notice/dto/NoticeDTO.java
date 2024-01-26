package com.wisehr.wisehr.notice.dto;

import jakarta.persistence.Access;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class NoticeDTO {
    private String notCode;
    private String notName;
    private String notComment;
    private int notView;
    private Date notCreateDate;
    private Date notModifyDate;
    private String notState;
    private SettingMemberDTO memCode;
}
