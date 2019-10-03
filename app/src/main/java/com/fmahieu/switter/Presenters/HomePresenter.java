package com.fmahieu.switter.Presenters;

import com.fmahieu.switter.ModelLayer.models.CurrentUser;

public class HomePresenter {

    private CurrentUser currentUser = CurrentUser.getUserInstance();

    public HomePresenter(){

    }

    public void getUserInfo(){}


    public void logOutUser(){
        currentUser.logOutUser();
    }
}
