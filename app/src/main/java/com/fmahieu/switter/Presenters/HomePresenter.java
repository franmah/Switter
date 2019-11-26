package com.fmahieu.switter.Presenters;

import com.fmahieu.switter.ModelLayer.ApplicationLogic.LoginLogic;
import com.fmahieu.switter.ModelLayer.ApplicationLogic.ProfileLogic;
import com.fmahieu.switter.ModelLayer.models.EncodedProfilePicture;
import com.fmahieu.switter.ModelLayer.models.MessageResult;
import com.fmahieu.switter.ModelLayer.models.singleton.Profile;

public class HomePresenter {

    private LoginLogic mLoginLogic = new LoginLogic();

    public HomePresenter(){

    }

    public MessageResult logOutUser(){
        return mLoginLogic.logUserOut();

    }

}
