package com.fmahieu.switter.Presenters;

import com.fmahieu.switter.ModelLayer.ApplicationLogic.FollowLogic;
import com.fmahieu.switter.ModelLayer.models.FollowResult;

public class FollowRecyclerPresenter {

    private FollowLogic mFollowLogic;

    public FollowRecyclerPresenter(){
        mFollowLogic = new FollowLogic();
    }

    public FollowResult getFollowingNextPage(String handle, String lastKey){
        return mFollowLogic.getFollowingNextPage(handle, lastKey);
    }

    public FollowResult getFollowersNextPage(String handle, String lastKey){
        return mFollowLogic.getFollowersNextPage(handle, lastKey);
    }

}
