package com.fmahieu.switter.Presenters;

import com.fmahieu.switter.ModelLayer.models.singleton.Profile;

public class SignInPresenter {

    // TODO: remove the ref to profile, only the app logic should have a ref to it
    private Profile mProfile = Profile.getUserInstance();

    public SignInPresenter(){

    }

    // TODO returnn a response
    public void connectUser(String hanlde, String password){
        // TODO: remove
        mProfile.setLoggedIn(true);

        // m
    }

}
