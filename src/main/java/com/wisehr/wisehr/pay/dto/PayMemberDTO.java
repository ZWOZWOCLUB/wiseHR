package com.wisehr.wisehr.pay.dto;

import com.wisehr.wisehr.setting.dto.SettingDepartmentDTO;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PayMemberDTO {
    private String memCode;
    private PayPositionDTO posCode;
    private PaySalaryDTO salCode;
}
