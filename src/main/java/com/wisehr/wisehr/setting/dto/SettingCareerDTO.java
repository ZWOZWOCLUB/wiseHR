package com.wisehr.wisehr.setting.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SettingCareerDTO {
    private String crrCode;
    private String crrName;
    private String crrPosition;
    private String crrStartDate;
    private String crrEndDate;
    private String crrState;
    private String crrDescription;
    private int memCode;
    private List<SettingCareerFileDTO> careerFile;


}
