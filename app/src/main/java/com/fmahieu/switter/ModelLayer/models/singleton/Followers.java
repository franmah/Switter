package com.fmahieu.switter.ModelLayer.models.singleton;


import com.fmahieu.switter.ModelLayer.models.Handle;
import com.fmahieu.switter.ModelLayer.models.User;

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

    private List<User> users;
    private Handle followersOwner; //handle of the user's followers to display in recycler

    private Followers(){
    }

    /** GETTERS AND SETTERS **/
    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Handle getFollowersOwner() {
        return followersOwner;
    }

    // When ContentFragment update the Followers singleton it fetches data using the owner handle.
    public void setFollowersOwner(Handle followersOwner) {
        this.followersOwner = followersOwner;
    }
}
