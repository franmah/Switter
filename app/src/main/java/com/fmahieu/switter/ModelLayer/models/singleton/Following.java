package com.fmahieu.switter.ModelLayer.models.singleton;

import com.fmahieu.switter.ModelLayer.models.Handle;
import com.fmahieu.switter.ModelLayer.models.User;

import java.util.List;

public class Following {

    private static Following following;

    public static Following getFollowingInstance() {
        if (following == null){
            following = new Following();
        }
        return following;
    }

    /** MEMBERS **/

    private List<User> users;
    private Handle followingOwner; //handle of the user's following to display in recycler

    private Following(){}


    /** GETTERS AND SETTERS **/

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Handle getFollowingOwner() {
        return followingOwner;
    }

    // when contentFragment will update the Following singleton it will fetch
    // the following of the owner.
    public void setFollowingOwner(Handle followingOwner) {
        this.followingOwner = followingOwner;
    }
}
