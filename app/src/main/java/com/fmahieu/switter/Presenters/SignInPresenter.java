package com.fmahieu.switter.Presenters;

import com.fmahieu.switter.ModelLayer.models.Profile;
import com.fmahieu.switter.Views.SignUpInfoActivity;

public class SignInPresenter {

    // TODO: remove the ref to profile, only the app logic should have a ref to it
    private Profile mProfile = Profile.getUserInstance();

    public SignInPresenter(){}

    // TODO returnn a response
    public void connectUser(){
        mProfile.setLoggedIn(true);
    }

}
