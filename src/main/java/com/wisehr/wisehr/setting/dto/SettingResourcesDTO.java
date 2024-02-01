package com.wisehr.wisehr.setting.dto;

import com.wisehr.wisehr.setting.entity.SettingSalary;
import lombok.*;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SettingResourcesDTO {
    private int memCode;
    private List<SettingCareerDTO> careerDTO;
    private List<SettingCertificateDTO> certificateDTO;
    private List<SettingDegreeDTO> degreeDTO;
    private SettingSalary salary;

}
