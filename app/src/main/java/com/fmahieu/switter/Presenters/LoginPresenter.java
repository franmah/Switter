package com.fmahieu.switter.Presenters;

import android.net.Uri;

import com.fmahieu.switter.ModelLayer.ApplicationLogic.LoginLogic;
import com.fmahieu.switter.ModelLayer.models.Handle;
import com.fmahieu.switter.ModelLayer.models.singleton.Profile;

public class LoginPresenter {

    private LoginLogic mLoginLogic = new LoginLogic();

    // TODO: REMOVE
    private Profile profile = Profile.getUserInstance();

    public LoginPresenter(){}

    public void signUserUp(Uri uri,String firstName, String lastName, String handle){

        mLoginLogic.signUpUser();

        // TODO: REMOVE
        profile.setLoggedIn(true);
        profile.setFirstName(firstName);
        profile.setLastName(lastName);
        profile.setHandle(new Handle(handle));
        profile.setNumFollowers(0);
        profile.setNumFollowing(0);
    }

    // TODO returnn a response
    public void connectUser(String hanlde, String password){
        // TODO: remove
        profile.setLoggedIn(true);

        // m
    }

}
