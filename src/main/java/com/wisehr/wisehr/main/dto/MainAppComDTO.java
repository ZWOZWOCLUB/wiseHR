package com.wisehr.wisehr.main.dto;

import com.wisehr.wisehr.main.entity.MainMember;
import lombok.*;

import java.time.LocalDate;
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MainAppComDTO {

    private String appCode;
    private String appState;
    private LocalDate appDate;

    private MainMember member;

}
