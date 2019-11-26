package com.fmahieu.switter.ModelLayer.models.httpModel;

public class IsFollowingResult {

    public boolean isFollowing;
    public String error;

    public IsFollowingResult(boolean isFollowing, String error) {
        this.isFollowing = isFollowing;
        this.error = error;
    }

    public boolean isFollowing() {
        return isFollowing;
    }

    public String getError() {
        return error;
    }
}
