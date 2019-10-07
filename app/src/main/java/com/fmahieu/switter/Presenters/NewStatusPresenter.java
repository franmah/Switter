package com.fmahieu.switter.Presenters;

import com.fmahieu.switter.ModelLayer.ApplicationLogic.NewStatusLogic;

public class NewStatusPresenter {

    private NewStatusLogic mNewStatusLogic;

    public NewStatusPresenter(){
        mNewStatusLogic = new NewStatusLogic();
    }

    public void sendNewStatus(){
        mNewStatusLogic.sendNewStatus();

    }
}
