package com.wisehr.wisehr.main.dto;


import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MainMemberDTO {

    private Long memCode;
    private String memName;
    private Long depCode;

    private List<MainAppComDTO> appComList;

}
