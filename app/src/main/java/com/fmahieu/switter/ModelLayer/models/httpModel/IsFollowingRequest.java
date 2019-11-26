package com.fmahieu.switter.ModelLayer.models.httpModel;

public class IsFollowingRequest {

    public String profile;
    public String user;
    public String token;

    public IsFollowingRequest(String profile, String user, String token) {
        this.profile = profile;
        this.user = user;
        this.token = token;

    }

    public String getProfile() {
        return profile;
    }

    public String getUser() {
        return user;
    }

    public String getToken() {
        return this.token;
    }
}
