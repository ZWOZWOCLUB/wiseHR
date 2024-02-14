package com.wisehr.wisehr.mypage.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MPDocumentDTO {
    private int memCode;
    private List<MPDocumentFileDTO> documentFileDTOList;
    private MPSalaryDTO salaryDTO;

}
