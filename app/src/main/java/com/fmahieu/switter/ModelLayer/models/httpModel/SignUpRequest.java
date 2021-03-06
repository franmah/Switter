package com.fmahieu.switter.ModelLayer.models.httpModel;

import com.fmahieu.switter.ModelLayer.models.EncodedProfilePicture;

public class SignUpRequest {

    private EncodedProfilePicture profilePicture;
    private String firstName;
    private String lastName;
    private String handle;
    private String password;
    private String email;

    public SignUpRequest(EncodedProfilePicture profilePicture, String firstName, String lastName, String handle,
                         String password, String email) {
        this.profilePicture = profilePicture;
        this.firstName = firstName;
        this.lastName = lastName;
        this.handle = handle;
        this.password = password;
        this.email = email;
    }


    public EncodedProfilePicture getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(EncodedProfilePicture profilePicture) {
        this.profilePicture = profilePicture;
    }

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

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}



