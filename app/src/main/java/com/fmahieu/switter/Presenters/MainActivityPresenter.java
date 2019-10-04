package com.fmahieu.switter.Presenters;

import com.fmahieu.switter.ModelLayer.models.Profile;

public class MainActivityPresenter {

    private Profile profile = Profile.getUserInstance();

    public MainActivityPresenter(){}

    public boolean isUserLoggedIn(){
        return profile.isLoggedIn();
    }
}
