package com.wisehr.wisehr.setting.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SettingSalaryDTO {
    private String salCode;
    private String salNumber;
    private String salBankName;
    private int memCode;
    private List<SettingSalaryFileDTO> salaryFile;
}
