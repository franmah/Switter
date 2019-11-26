package com.fmahieu.switter.Presenters;

import com.fmahieu.switter.ModelLayer.ApplicationLogic.StatusContentLogic;
import com.fmahieu.switter.ModelLayer.models.StatusContentResult;

public class StatusRecyclerPresenter {

    private StatusContentLogic mUpdateStatusContentLogic;

    public StatusRecyclerPresenter(){
        mUpdateStatusContentLogic = new StatusContentLogic();
    }


    public StatusContentResult getFeed(String handle, String lastKey){
        return mUpdateStatusContentLogic.getFeed(handle, lastKey);
    }

    public StatusContentResult getStory(String handle, String lastKey){
        return mUpdateStatusContentLogic.getStory(handle, lastKey);
    }

    public StatusContentResult getHashtagFeed(String hashtag, String lastKey){
        return mUpdateStatusContentLogic.getHashtagFeed(hashtag, lastKey);
    }
}
