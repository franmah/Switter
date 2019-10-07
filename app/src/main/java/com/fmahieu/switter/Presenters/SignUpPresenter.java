package com.fmahieu.switter.Presenters;

import com.fmahieu.switter.ModelLayer.ApplicationLogic.LoginLogic;
import com.fmahieu.switter.ModelLayer.models.Handle;
import com.fmahieu.switter.ModelLayer.models.singleton.Profile;

public class SignUpPresenter {

    private LoginLogic mLoginLogic = new LoginLogic();

    // TODO: REMOVE
    private Profile profile = Profile.getUserInstance();

    public SignUpPresenter(){}

    public void signUserUp(){

        mLoginLogic.signUpUser();

        // TODO: REMOVE
        profile.setLoggedIn(true);
        profile.setFirstName("Mister");
        profile.setLastName("Test");
        profile.setHandle(new Handle("@amazingHandle"));
        profile.setNumFollowers(0);
        profile.setNumFollowing(0);
    }

}
