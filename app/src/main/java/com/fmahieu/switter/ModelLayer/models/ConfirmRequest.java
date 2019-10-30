package com.fmahieu.switter.ModelLayer.models;

public class ConfirmRequest {

    private String code;
    private String username;
    private String password;

    public ConfirmRequest(String code, String username, String password) {
        this.code = code;
        this.username = username;
        this.password = password;
    }

    public String getCode() {
        return code;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
