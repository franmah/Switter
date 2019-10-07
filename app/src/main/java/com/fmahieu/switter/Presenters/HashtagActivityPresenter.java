package com.fmahieu.switter.Presenters;

import com.fmahieu.switter.ModelLayer.models.singleton.HashtagFeed;

public class HashtagActivityPresenter {

    public HashtagActivityPresenter(){}

    /**
     * Update the hashtag to show in HashtagFeed. Retrieve first page for the HashtagFeed
     * @param hashtag
     */
    public void updateHashtagFeed(String hashtag){
        // TODO: update
        HashtagFeed.getInstance().setHashtag(hashtag);

        // get first page.
    }
}
