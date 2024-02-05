package com.wisehr.wisehr.security.auth.model.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Builder

public class TokenDTO {
    // 토큰 정보담을 DTO
    private String grantType;   //토큰 타입
    private String memCode;     //인증받은 회원 ID
    private String accessToken; // 엑세스 토큰p
}
