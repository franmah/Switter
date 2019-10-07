package com.fmahieu.switter.ModelLayer.models.singleton;

import android.net.Uri;

import com.fmahieu.switter.ModelLayer.models.Handle;

public class UserUnique {

    private static UserUnique user;

    public static UserUnique getUserInstance() {
        if (user == null){
            user = new UserUnique();
        }
        return user;
    }

    private UserUnique(){
    }

    private String firstName;
    private String lastName;
    private Handle handle;
    private Uri picture;
    private int numFollowers;
    private int numFollowing;
    private boolean isFollowedByProfile;

    /** GETTERS AND SETTERS **/
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Handle getHandle() {
        return handle;
    }

    public void setHandle(Handle handle) {
        this.handle = handle;
    }

    public Uri getPicture() {
        return picture;
    }

    public void setPicture(Uri picture) {
        this.picture = picture;
    }

    public int getNumFollowers() {
        return numFollowers;
    }

    public void setNumFollowers(int numFollowers) {
        this.numFollowers = numFollowers;
    }

    public int getNumFollowing() {
        return numFollowing;
    }

    public void setNumFollowing(int numFollowing) {
        this.numFollowing = numFollowing;
    }

    public boolean isFollowedByProfile() {
        return isFollowedByProfile;
    }

    public void setFollowedByProfile(boolean followedByProfile) {
        isFollowedByProfile = followedByProfile;
    }
}
