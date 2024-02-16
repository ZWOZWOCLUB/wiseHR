package com.wisehr.wisehr.approval.dto;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class ApprovalPosDTO {
    private Long posCode;
    private String posName;
    private Long posSalary;
}
