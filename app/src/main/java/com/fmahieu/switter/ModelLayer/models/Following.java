package com.fmahieu.switter.ModelLayer.models;

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

    private List<Handle> userHanles;
    private Handle followingOwner; //handle of the user's following to display in recycler

    private Following(){
    }


    /** GETTERS AND SETTERS **/

    public List<Handle> getUserHanles() {
        return userHanles;
    }

    public void setUserHanles(List<Handle> userHanles) {
        this.userHanles = userHanles;
    }

    public Handle getFollowingOwner() {
        return followingOwner;
    }

    public void setFollowingOwner(Handle followingOwner) {
        this.followingOwner = followingOwner;
    }
}
