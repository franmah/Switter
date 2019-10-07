package com.fmahieu.switter.Presenters;


import com.fmahieu.switter.ModelLayer.ApplicationLogic.UpdateFeedLogic;
import com.fmahieu.switter.ModelLayer.ApplicationLogic.UpdateFollowersLogic;
import com.fmahieu.switter.ModelLayer.ApplicationLogic.UpdateFollowingLogic;
import com.fmahieu.switter.ModelLayer.ApplicationLogic.UpdateStoryLogic;

public class ContentPresenter {

    private UpdateFeedLogic mUpdateFeedLogic;
    private UpdateStoryLogic mUpdateStoryLogic;
    private UpdateFollowingLogic mUpdateFollowingLogic;
    private UpdateFollowersLogic mUpdateFollowersLogic;


    public ContentPresenter(){
        mUpdateFeedLogic = new UpdateFeedLogic();
        mUpdateStoryLogic = new UpdateStoryLogic();
        mUpdateFollowingLogic = new UpdateFollowingLogic();
        mUpdateFollowersLogic = new UpdateFollowersLogic();
    }

    // retrieve updated first page of feed
    public void updateFeed(){
        mUpdateFeedLogic.getFeed();

    }

    // retrieve updated first page of story
    public void updateStory(){
       mUpdateStoryLogic.getStory();

    }

    // retrieve new profile following list
    public void updateFollowing(){
        mUpdateFollowingLogic.getFollowing();

    }

    // retrieve new profile followers list
    public void updateFollowers(){
        mUpdateFollowersLogic.getFollowers();

    }
}
