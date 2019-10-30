package com.fmahieu.switter.Presenters;

import android.net.Uri;

import com.fmahieu.switter.ModelLayer.ApplicationLogic.LoginLogic;
import com.fmahieu.switter.ModelLayer.ApplicationLogic.UpdateProfileLogic;
import com.fmahieu.switter.ModelLayer.models.Image;
import com.fmahieu.switter.ModelLayer.models.singleton.Feed;
import com.fmahieu.switter.ModelLayer.models.singleton.Followers;
import com.fmahieu.switter.ModelLayer.models.singleton.Following;
import com.fmahieu.switter.ModelLayer.models.singleton.Profile;
import com.fmahieu.switter.ModelLayer.models.singleton.Story;

public class HomePresenter {

    private LoginLogic mLoginLogic = new LoginLogic();
    private UpdateProfileLogic mUpdateProfileLogic = new UpdateProfileLogic();

    private Profile mProfile = Profile.getUserInstance();

    public HomePresenter(){

    }

    public Image getProfilePicture(){
        return mProfile.getPicture();
    }


    public void logOutUser(){
        mLoginLogic.logUserOut();

        // TODO: remove
        mProfile.logOutUser();
    }

    public void updateProfile(){
        mUpdateProfileLogic.updateProfile();
    }


    // setUpFeed so it knows what to show (which user)
    public void setUpFeedOwner(){
        // tell the feed to display the current user (profile)'  s feed.
        Feed.getFeedInstance().setFeedOwner(Profile.getUserInstance().getHandle());
    }

    public void setUpStoryOwner(){
        Story.getStoryInstance().setStoryOwner(Profile.getUserInstance().getHandle());

    }

    public void setUpFollowersOwner() {
        Followers.getFollowersInstance().setFollowersOwner(Profile.getUserInstance().getHandle());

    }


    public void setUpFollowingOwner(){
        Following.getFollowingInstance().setFollowingOwner(Profile.getUserInstance().getHandle());

    }
}
