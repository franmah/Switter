package com.fmahieu.switter.Presenters;

import com.fmahieu.switter.ModelLayer.ApplicationLogic.UpdateProfileLogic;
import com.fmahieu.switter.ModelLayer.ApplicationLogic.UpdateUniqueUserLogic;
import com.fmahieu.switter.ModelLayer.models.singleton.Feed;
import com.fmahieu.switter.ModelLayer.models.singleton.Followers;
import com.fmahieu.switter.ModelLayer.models.singleton.Following;
import com.fmahieu.switter.ModelLayer.models.Handle;
import com.fmahieu.switter.ModelLayer.models.singleton.Profile;
import com.fmahieu.switter.ModelLayer.models.singleton.Story;

public class UserActivityPresenter {

    private UpdateProfileLogic mUpdateProfileLogic;
    private UpdateUniqueUserLogic mUpdateUniqueUserLogic;

    public UserActivityPresenter(){
        mUpdateUniqueUserLogic = new UpdateUniqueUserLogic();
        mUpdateProfileLogic = new UpdateProfileLogic();
    }

    // retrieve user data and fill user singleton
    public void updateUserInfo(String handleString){

        if(handleString.equals(Profile.getUserInstance().getHandle().getHandleString())){
            mUpdateProfileLogic.updateProfile();
        }
        else{
            mUpdateUniqueUserLogic.updateUniqueUserInfo();
        }


        setInstancesUp(handleString);
    }

    // set the owner of the feed, story, following/ers singletons
    public void setInstancesUp( String handleString ){

        Handle handle = new Handle( handleString );
        Feed.getFeedInstance().setFeedOwner( handle );
        Story.getStoryInstance().setStoryOwner( handle );
        Following.getFollowingInstance().setFollowingOwner( handle );
        Followers.getFollowersInstance().setFollowersOwner( handle );
    }

    public void followUser(){
        mUpdateProfileLogic.followUser();
    }

    public void unFollowUser(){
        mUpdateProfileLogic.unfollowUser();

    }

    // check if profile follows user
    public boolean isProfileFollowingUser(Handle handle){
        return true;
    }
}
