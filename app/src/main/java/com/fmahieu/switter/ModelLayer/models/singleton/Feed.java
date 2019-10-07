package com.fmahieu.switter.ModelLayer.models.singleton;

import com.fmahieu.switter.ModelLayer.models.Handle;
import com.fmahieu.switter.ModelLayer.models.Status;

import java.util.List;

public class Feed {

    private static Feed feed;

    public static Feed getFeedInstance() {
        if (feed == null){
            feed = new Feed();
        }
        return feed;
    }

    /** MEMBERS **/

    private List<Status> statuses;
    private Handle feedOwner; // Handle of user to display the feed


    private Feed() {
    }



    /** GETTERS AND SETTERS **/

    public List<Status> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<Status> statuses) {
        this.statuses = statuses;
    }

    public Handle getFeedOwner() {
        return feedOwner;
    }

    public void setFeedOwner(Handle feedOwner) {
        this.feedOwner = feedOwner;
    }
}
