package com.wisehr.wisehr.security.auth.model.dto;

import lombok.*;

import java.util.LinkedHashMap;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AuthorityDTO {

    private int authorityCode;
    private String authorityName;



    public static AuthorityDTO fromLinkedHashMap(LinkedHashMap<String, Object> map) {
        int authorityCode = (Integer) map.get("authCode");
        String authorityName = (String) map.get("authName");

        return new AuthorityDTO(authorityCode, authorityName);

    }
}
