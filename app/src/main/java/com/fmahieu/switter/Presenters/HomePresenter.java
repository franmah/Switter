package com.fmahieu.switter.Presenters;

import android.net.Uri;

import com.fmahieu.switter.ModelLayer.models.Feed;
import com.fmahieu.switter.ModelLayer.models.Followers;
import com.fmahieu.switter.ModelLayer.models.Following;
import com.fmahieu.switter.ModelLayer.models.Profile;
import com.fmahieu.switter.ModelLayer.models.Story;

public class HomePresenter {

    private Profile mProfile = Profile.getUserInstance();

    public HomePresenter(){

    }

    public Uri getProfilePicture(){
        return mProfile.getPicture();
    }


    public void logOutUser(){
        mProfile.logOutUser();
    }


    // setUpFeed so it knows what to show (which user)
    public void setUpFeedUser(){
        // tell the feed to display the current user (profile)'  s feed.
        Feed.getFeedInstance().setFeedOwner(Profile.getUserInstance().getHandle());
    }

    public void setUpStoryUser(){
        Story.getStoryInstance().setStoryOwner(Profile.getUserInstance().getHandle());

    }

    public void setUpFollowersUser() {
        Followers.getFollowersInstance().setFollowersOwner(Profile.getUserInstance().getHandle());

    }


    public void setUpFollowingUser(){
        Following.getFollowingInstance().setFollowingOwner(Profile.getUserInstance().getHandle());

    }
}
