package com.fmahieu.switter.ModelLayer.models;

import android.net.Uri;

public class User {

    private String firstName;
    private String lastName;
    private Handle handle;
    private Uri profilePicture;

    public User(String firstName, String lastName, Handle handle, Uri profilePicture) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.handle = handle;
        this.profilePicture = profilePicture;
    }

    /** GETTERS AND SETTERS **/

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Handle getHandle() {
        return handle;
    }

    public void setHandle(Handle handle) {
        this.handle = handle;
    }

    public Uri getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(Uri profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
