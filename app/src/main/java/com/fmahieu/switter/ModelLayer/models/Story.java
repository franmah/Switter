package com.fmahieu.switter.ModelLayer.models;

import java.util.List;

public class Story {

    private static Story story;

    public static Story getStoryInstance() {
        if (story == null){
            story = new Story();
        }
        return story;
    }

    /** MEMBERS **/

    private List<Status> statuses;
    private Handle storyOwner; //handle of the user's story to display in recycler

    private Story(){
    }


    /** GETTERS AND SETTER **/

    public List<Status> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<Status> statuses) {
        this.statuses = statuses;
    }

    public Handle getStoryOwner() {
        return storyOwner;
    }

    public void setStoryOwner(Handle storyOwner) {
        this.storyOwner = storyOwner;
    }
}
