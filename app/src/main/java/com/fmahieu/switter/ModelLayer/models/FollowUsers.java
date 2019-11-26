package com.fmahieu.switter.ModelLayer.models;

import java.util.List;

public class FollowUsers {

    public List<User> followUsers;
    public String error;

    public List<User> getFollowUsers(){return this.followUsers;}
    public String getError(){return this.error;}
}
