package com.wisehr.wisehr.setting.dto;

import jakarta.persistence.Column;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SettingSalaryFileDTO {
    private int salAtcCode;
    private String salAtcName;
    private String salAtcDeleteStatus;
    private String salAtcPath;
    private String salAtcRegistDate;
    private String salCode;
    private String salAtcConvertName;
}
