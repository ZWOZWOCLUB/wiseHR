package com.wisehr.wisehr.security.user.controller;

public class LoginRequest {
    private String memCode;
    private String memPassword;

    public LoginRequest() {
    }

    public LoginRequest(String memCode, String memPassword) {
        this.memCode = memCode;
        this.memPassword = memPassword;
    }

    public String getMemCode() {
        return memCode;
    }

    public void setMemCode(String memCode) {
        this.memCode = memCode;
    }

    public String getMemPassword() {
        return memPassword;
    }

    public void setMemPassword(String memPassword) {
        this.memPassword = memPassword;
    }
}
