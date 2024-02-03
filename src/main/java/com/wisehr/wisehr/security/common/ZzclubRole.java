package com.wisehr.wisehr.security.common;

public enum ZzclubRole {
    일반사원("USER"),
    중간관리자("ADMIN"),
    최고관리자("SUPER"),
    전체("USER, ADMIN, SUPER");


    private final String memRole;

    ZzclubRole(String memRole){
        this.memRole = memRole;
    }

    public String getMemRole(){
        return memRole;
    }
}
