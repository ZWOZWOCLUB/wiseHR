package com.wisehr.wisehr.mypage.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class DocumentDTO {
    private int memCode;
    private List<DocumentFileDTO> documentFileDTOList;
    private SalaryDTO salaryDTO;

}
