package com.wisehr.wisehr.approval.dto;

import com.wisehr.wisehr.approval.entity.ApprovalWeekDay;
import com.wisehr.wisehr.approval.entity.ApprovalWorkPattern;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ApprovalPatternDayDTO {
    private ApprovalWeekDayDTO weekDay;
    private ApprovalWorkPatternDTO workPattern;
}
