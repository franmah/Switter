package com.fmahieu.switter.ServerCommunication;

import com.fmahieu.switter.ModelLayer.ApplicationLogic.HttpFacadeInterface;
import com.fmahieu.switter.ModelLayer.models.NewStatusRequest;
import com.fmahieu.switter.ModelLayer.models.ProfilePictureRequest;
import com.fmahieu.switter.ModelLayer.models.SignInRequest;
import com.fmahieu.switter.ModelLayer.models.SetFollowUserRequest;
import com.fmahieu.switter.ModelLayer.models.httpModel.IsFollowingRequest;
import com.fmahieu.switter.ModelLayer.models.httpModel.LogOutRequest;
import com.fmahieu.switter.ModelLayer.models.httpModel.SignUpRequest;
import com.fmahieu.switter.ModelLayer.models.Status;

public class HttpFacade implements HttpFacadeInterface {

    ContentHttp mContentHttp = new ContentHttp();
    ProfileHttp mProfileHttp;
    UserHttp mUserHttp;
    NewStatusHttpRequest mNewStatusHttpRequest;

    @Override
    public String getFeedFromServer(String handle, String lastKey, String token) throws Exception {
        return mContentHttp.getFeedFromServer(handle, lastKey, token);
    }

    @Override
    public String getStoryFromServer(String handle, String lastKey, String token) throws Exception {
        return mContentHttp.getStoryFromServer(handle, lastKey, token);
    }

    @Override
    public String getHashtagFeedFromServer(String handle, String lastKey, String token) throws Exception{
        return mContentHttp.getHashtagFeedFromServer(handle, lastKey, token);
    }

    @Override
    public String getFollowingFromServer(String handle, String lastKey, String token) throws Exception {
        return mContentHttp.getFollowingFromServer(handle, lastKey, token);
    }

    @Override
    public String getFollowersFromServer(String handle, String lastKey, String token) throws Exception {
        return mContentHttp.getFollowersFromServer(handle, lastKey, token);
    }

    @Override
    public void sendNewStatus(NewStatusRequest request) throws  Exception{
        mNewStatusHttpRequest = new NewStatusHttpRequest();
        mNewStatusHttpRequest.sendNewStatus(request);
    }

    @Override
    public String followUser(SetFollowUserRequest request) throws Exception {
        mProfileHttp = new ProfileHttp();
        return mProfileHttp.followUser(request);
    }

    @Override
    public String isProfileFollowingUser(IsFollowingRequest request) throws Exception {
        mProfileHttp = new ProfileHttp();
        return mProfileHttp.isProfileFollowingUser(request);
    }

    @Override
    public String updateProfilePicture(ProfilePictureRequest request) throws Exception {
        mProfileHttp = new ProfileHttp();
        return mProfileHttp.updateProfilePicture(request);
    }

    @Override
    public String signUserUp(SignUpRequest request) throws Exception {
        mProfileHttp = new ProfileHttp();
        return mProfileHttp.signUpUser(request);
    }

    @Override
    public String getUserFromServer(String handle) throws Exception{
        mUserHttp = new UserHttp();
        return mUserHttp.getUserFromServer(handle);
    }

    @Override
    public String signInUser(SignInRequest request) throws Exception {
        mProfileHttp = new ProfileHttp();
        return mProfileHttp.signInUser(request);
    }

    @Override
    public String logOutUser(LogOutRequest request) throws Exception {
        mProfileHttp = new ProfileHttp();
        return mProfileHttp.logOutUser(request);
    }
}
