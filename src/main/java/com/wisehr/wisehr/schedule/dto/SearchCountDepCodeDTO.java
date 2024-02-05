package com.wisehr.wisehr.schedule.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SearchCountDepCodeDTO {
    private int depCode;
    private String depName;
    private int count;
}
