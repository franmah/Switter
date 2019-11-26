package com.fmahieu.switter.ModelLayer.models;


public class UserResult {

    public User user;
    public String error;

    public UserResult(User user, String error) {
        this.user = user;
        this.error = error;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
