package com.wisehr.wisehr.security.common;

public enum ZzclubRole {
    USER("USER"),
    ADMIN("ADMIN"),
    SUPERADMIN("SUPERADMIN"),
    ALL("USER, ADMIN, SUPER");


    private final String memRole;

    ZzclubRole(String memRole){
        this.memRole = memRole;
    }

    public String getMemRole(){
        return memRole;
    }
}
