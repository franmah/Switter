package com.fmahieu.switter.Presenters;

import com.fmahieu.switter.ModelLayer.models.Profile;

public class SignUpPresenter {

    private Profile profile = Profile.getUserInstance();

    public SignUpPresenter(){}

    public void signUserUp(){
        profile.setLoggedIn(true);
    }

}
