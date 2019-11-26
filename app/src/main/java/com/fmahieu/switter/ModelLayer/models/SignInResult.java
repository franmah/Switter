package com.fmahieu.switter.ModelLayer.models;

public class SignInResult {

    public User user;
    public String token;
    public String error;

    public SignInResult(User user, String token, String error) {
        this.user = user;
        this.token = token;
        this.error = error;
    }

    public User getUser() {
        return user;
    }

    public String getToken() {
        return token;
    }

    public String getError() {
        return error;
    }
}
