package com.fmahieu.switter.Presenters;

import com.fmahieu.switter.ModelLayer.ApplicationLogic.UpdateFollowersLogic;
import com.fmahieu.switter.ModelLayer.ApplicationLogic.UpdateFollowingLogic;

public class FollowRecyclerPresenter {

    private UpdateFollowingLogic mUpdateFollowingLogic;
    private UpdateFollowersLogic mUpdateFollowersLogic;

    public FollowRecyclerPresenter(){
        mUpdateFollowersLogic = new UpdateFollowersLogic();
        mUpdateFollowingLogic = new UpdateFollowingLogic();
    }

    public void getFollowNextPage(){

    }

    public void getFollowersNextPage(){}

}
