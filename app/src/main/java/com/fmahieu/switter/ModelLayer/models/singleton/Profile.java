package com.fmahieu.switter.ModelLayer.models.singleton;

import android.net.Uri;

import com.fmahieu.switter.ModelLayer.models.EncodedProfilePicture;
import com.fmahieu.switter.ModelLayer.models.LinkProfilePicture;
import com.fmahieu.switter.ModelLayer.models.User;

import java.util.List;

public class Profile {

    private static Profile user;

    public static Profile getUserInstance() {
        if (user == null){
            user = new Profile();
        }
        return user;
    }

    private Profile(){
    }

    private boolean isLoggedIn = false;
    private boolean needToConfirmSignUp;
    private String firstName;
    private String lastName;
    private String handle;
    private String authToken;
    private LinkProfilePicture profilePictureLink;

    public void logOutUser(){
        isLoggedIn = false;
    }

    public void setUpProfileWithUser(User user){
        this.needToConfirmSignUp = false;
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.handle = user.getHandle();
        LinkProfilePicture link = user.getProfilePicture();
        this.profilePictureLink = user.getProfilePicture();
    }

    /** GETTERS AND SETTERS **/

    public boolean needToConfirmSignUp() {
        return needToConfirmSignUp;
    }

    public void setNeedToConfirmSignUp(boolean needToConfirmSignUp) {
        this.needToConfirmSignUp = needToConfirmSignUp;
    }

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

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public LinkProfilePicture getProfilePictureLink() {
        return profilePictureLink;
    }

    public void setProfilePictureLink(LinkProfilePicture profilePictureLink) {
        this.profilePictureLink = profilePictureLink;
    }
}
