package com.wisehr.wisehr.comment.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class ComMemberDTO {
    private Long memCode;
    private String memName;
    private String memPhone;
    private String memEmail;
    private String memAddress;
    private String memBirth;
    private String memPassword;
    private String memHireDate; //입사일
    private String memEndDate;  //퇴직일
    private String memStatus;   //퇴사상태
    private String memRoll;     //권한
    private ComPositionDTO posCode;       //직위코드
    private Long depCode;       //부서코드
}
