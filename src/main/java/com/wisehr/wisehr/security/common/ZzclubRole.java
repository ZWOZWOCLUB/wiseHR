package com.wisehr.wisehr.security.common;

public enum ZzclubRole {
    USER("USER"),
    ADMIN("ADMIN"),
    SUPER("SUPER_ADMIN"),
    ALL("USER, ADMIN, SUPER_ADMIN");

    private String memRole;

    ZzclubRole(String mem_role){
        this.memRole = memRole;
    }

    public String getMemRole(){
        return memRole;
    }
}
