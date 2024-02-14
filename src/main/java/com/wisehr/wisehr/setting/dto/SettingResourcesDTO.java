package com.wisehr.wisehr.setting.dto;

import com.wisehr.wisehr.setting.entity.SettingCareerFile;
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
    private SettingSalaryDTO salary;
    private SettingSalaryFileDTO salaryFileDTO;
    private List<SettingCareerFileDTO> careerFileDTO;
    private List<SettingCertificateFileDTO> certificateFileDTO;
    private List<SettingDegreeFileDTO> degreeFileDTO;
    private List<SettingDocumentFileDTO> documentFileDTO;

}
