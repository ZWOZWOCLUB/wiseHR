package com.wisehr.wisehr.notice.dto;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class NotCommentDTO {
    private Long comCode;
    private String comContents;
    private String comDate;
    private Long memCode;
    private String comDeleteState;
    private String notCode;
}
