package com.wisehr.wisehr.alarmAndMessage.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AAMReferencerDTO {
    private int memCode;
    private String appCode;
    private String payCode;
    private String payKind;
}
