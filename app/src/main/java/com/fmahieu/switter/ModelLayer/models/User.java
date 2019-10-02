package com.fmahieu.switter.ModelLayer.models;

import java.util.List;

public class User {

    private static User user;

    public static User getUserInstance() {
        if (user == null){
            user = new User();
        }
        return user;
    }

    private User(){
        // TODO: Remove:
        setLoggedIn(false);
    }

    private boolean isLoggedIn;
    private String firstName;
    private String lastNmae;
    private Handle handle;
    //private Picture picture;
    private List<Handle> followers;
    private List<Handle> following;


    /** GETTERS AND SETTERS **/

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastNmae() {
        return lastNmae;
    }

    public void setLastNmae(String lastNmae) {
        this.lastNmae = lastNmae;
    }

    public Handle getHandle() {
        return handle;
    }

    public void setHandle(Handle handle) {
        this.handle = handle;
    }

    public List<Handle> getFollowers() {
        return followers;
    }

    public void setFollowers(List<Handle> followers) {
        this.followers = followers;
    }

    public List<Handle> getFollowing() {
        return following;
    }

    public void setFollowing(List<Handle> following) {
        this.following = following;
    }
}
