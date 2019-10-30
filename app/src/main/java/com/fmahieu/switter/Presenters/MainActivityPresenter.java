package com.fmahieu.switter.Presenters;

import android.content.Context;

import com.fmahieu.switter.ModelLayer.ApplicationLogic.LoginLogic;
import com.fmahieu.switter.ModelLayer.models.singleton.Profile;

public class MainActivityPresenter {

    private Profile profile = Profile.getUserInstance();
    private LoginLogic mLoginLogic = new LoginLogic();

    public MainActivityPresenter(){}

    public boolean isUserLoggedIn(){
        return profile.isLoggedIn();
    }

    public void initializeAuth(Context context){
        mLoginLogic.initializeAuth(context);
    }
}
