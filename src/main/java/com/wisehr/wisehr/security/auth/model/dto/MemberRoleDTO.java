package com.wisehr.wisehr.security.auth.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor

public class MemberRoleDTO {

    private int memCode;

    private int authorityCode;

    private AuthorityDTO authority;



    public MemberRoleDTO(int memCode, int authorityCode, AuthorityDTO authority) {
        this.memCode = memCode;
        this.authorityCode = authorityCode;
        this.authority = authority;
    }


//    public MemberRoleDTO(int memCode, int authorityCode) {
//        this.memCode = memCode;
//        this.authorityCode = authorityCode;
//    }

//    public MemberRoleDTO(int memCode, int authorityCode, AuthorityDTO authority) {
//        this.memCode = memCode;
//        this.authorityCode = authorityCode;
//        this.authority = authority;
//    }
}
