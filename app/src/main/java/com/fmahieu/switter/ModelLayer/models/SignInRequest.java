package com.fmahieu.switter.ModelLayer.models;

public class SignInRequest {

    private String password;
    private String handle;

    public SignInRequest(String password, String username) {
        this.password = password;
        this.handle = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }
}
