package com.wisehr.wisehr.approval.dto;


import jakarta.persistence.Access;
import jakarta.persistence.SecondaryTable;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class ApprovalDateDTO {

    private String proStartDate;
    private String proEndDate;
    private Long memCode;

}
