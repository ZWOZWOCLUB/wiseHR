package com.wisehr.wisehr.approval.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wisehr.wisehr.approval.entity.ReceiveAramMember;
import lombok.*;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ApprovalPerAlarmDTO {
    private int perArmCode;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+9")
    private Date perArmDateTime;
    private String perArmCheckStatus;
    private ReceiveAramMemberDTO receiveAramMember;
}
