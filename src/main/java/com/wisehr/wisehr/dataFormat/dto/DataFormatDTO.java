package com.wisehr.wisehr.dataFormat.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class DataFormatDTO {
    private Long dataCode;
    private String dataName;
    private DataMemberDTO dataMember;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date registDate;
    private Long dataSize;
    private String dataPath;
    private String dataDeleteStatus;
}
