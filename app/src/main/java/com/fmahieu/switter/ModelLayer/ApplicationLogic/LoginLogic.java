package com.fmahieu.switter.ModelLayer.ApplicationLogic;

import com.fmahieu.switter.ModelLayer.models.singleton.Profile;

public class LoginLogic {

    Profile mProfile = Profile.getUserInstance();

    public LoginLogic(){}

    public void connectUser(){}

    public void signUpUser(){}

    public void logUserOut(){
        mProfile.logOutUser();
    }
}
