package com.fmahieu.switter.ModelLayer.models;


import java.util.List;

/**
 * Tell the FollowRecycler which list of followers to use
 */
public class Followers {
    private static Followers followers;

    public static Followers getFollowersInstance() {
        if (followers == null){
            followers = new Followers();
        }
        return followers;
    }

    /** MEMBERS **/

    private List<Handle> userHanles;
    private Handle followersOwner; //handle of the user's followers to display in recycler

    private Followers(){
    }

    /** GETTERS AND SETTERS **/
    public List<Handle> getUserHanles() {
        return userHanles;
    }

    public void setUserHanles(List<Handle> userHanles) {
        this.userHanles = userHanles;
    }

    public Handle getFollowersOwner() {
        return followersOwner;
    }

    public void setFollowersOwner(Handle followersOwner) {
        this.followersOwner = followersOwner;
    }
}
