package com.fmahieu.switter.ModelLayer.models;

import android.net.Uri;

public class User {

    private String firstName;
    private String lastName;
    private Handle handle;
    private Image profilePicture;

    public User(String firstName, String lastName, Handle handle, Image profilePicture) {
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

    public Image getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(Image profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
