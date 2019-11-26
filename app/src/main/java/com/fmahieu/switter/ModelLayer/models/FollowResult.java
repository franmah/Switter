package com.fmahieu.switter.ModelLayer.models;

import java.util.List;

public class FollowResult {
    public List<User> followUsers;
    public String error;
    public String lastKey;

    public FollowResult(List<User> users, String errorMessage) {
        this.followUsers = users;
        this.error = errorMessage;
    }

    public List<User> getUsers() {
        return followUsers;
    }

    public String getErrorMessage() {
        return error;
    }

    public String getLastKey() {
        return lastKey;
    }
}
