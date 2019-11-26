package com.fmahieu.switter.ModelLayer.models;

public class SetFollowUserRequest {

    public String profile;
    public String user;
    public boolean follow;
    public String token;

    public SetFollowUserRequest(String profile, String user, boolean follow, String token) {
        this.profile = profile;
        this.user = user;
        this.follow = follow;
        this.token = token;
    }

    public String getProfile() {
        return profile;
    }

    public String getUser() {
        return user;
    }

    public boolean isFollow() {
        return follow;
    }

    public String getToken() {
        return token;
    }
}

