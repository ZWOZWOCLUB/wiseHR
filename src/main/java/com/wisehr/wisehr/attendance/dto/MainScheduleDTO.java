package com.wisehr.wisehr.attendance.dto;

import lombok.*;

import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MainScheduleDTO {

    private String schCode;
    private String schType;
    private LocalDate schStartDate;
    private LocalDate schEndDate;

}
