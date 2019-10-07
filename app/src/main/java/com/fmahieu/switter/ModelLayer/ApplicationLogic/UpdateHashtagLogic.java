package com.fmahieu.switter.ModelLayer.ApplicationLogic;

import com.fmahieu.switter.ModelLayer.models.singleton.HashtagFeed;

public class UpdateHashtagLogic {

    private HashtagFeed mHashtagFeed;

    public UpdateHashtagLogic(){
        mHashtagFeed = HashtagFeed.getInstance();
    }

    public void getHashtagFeed(){}

    public void getHashtagFeedNextPage(){}
}
