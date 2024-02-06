package com.wisehr.wisehr.notice.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class NotAllAlarmDTO {
    private int allArmCode;
    private LocalDateTime allArmDate;
    private String allArmCheck;
    private String notCode;
    private NotMemberDTO memCode;
}
