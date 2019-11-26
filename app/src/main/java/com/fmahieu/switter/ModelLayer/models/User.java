package com.fmahieu.switter.ModelLayer.models;


public class User {

    public String firstName;
    public String lastName;
    public String handle;
    public LinkProfilePicture profilePicture;

    public User(String firstName, String lastName, String handle, LinkProfilePicture profilePicture) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.handle = handle;
        this.profilePicture = profilePicture;
    }

    public User(){}

    /** GETTERS AND SETTERS **/

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public LinkProfilePicture getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(LinkProfilePicture profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

}
