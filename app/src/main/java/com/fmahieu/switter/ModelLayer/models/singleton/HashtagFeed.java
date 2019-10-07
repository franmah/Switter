package com.fmahieu.switter.ModelLayer.models.singleton;


import com.fmahieu.switter.ModelLayer.models.Hashtag;
import com.fmahieu.switter.ModelLayer.models.Status;

import java.util.List;

public class HashtagFeed  {

    private static HashtagFeed sHashtagFeed;

    public static HashtagFeed getInstance(){
        if(sHashtagFeed == null){
            sHashtagFeed = new HashtagFeed();
        }
        return sHashtagFeed;
    }


    private HashtagFeed(){}

    /** MEMBERS **/

    private List<Status> statuses;
    private String hashtag;


    /** GETTERS AND SETTERS **/

    public List<Status> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<Status> statuses) {
        this.statuses = statuses;
    }

    public String getHashtag() {
        return hashtag;
    }

    public void setHashtag(String hashtag) {
        this.hashtag = hashtag;
    }
}
