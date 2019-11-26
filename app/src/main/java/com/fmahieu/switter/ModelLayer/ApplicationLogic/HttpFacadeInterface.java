package com.fmahieu.switter.ModelLayer.ApplicationLogic;

import com.fmahieu.switter.ModelLayer.models.NewStatusRequest;
import com.fmahieu.switter.ModelLayer.models.ProfilePictureRequest;
import com.fmahieu.switter.ModelLayer.models.SignInRequest;
import com.fmahieu.switter.ModelLayer.models.SetFollowUserRequest;
import com.fmahieu.switter.ModelLayer.models.httpModel.IsFollowingRequest;
import com.fmahieu.switter.ModelLayer.models.httpModel.LogOutRequest;
import com.fmahieu.switter.ModelLayer.models.httpModel.SignUpRequest;

public interface HttpFacadeInterface {

    String getFeedFromServer(String handle, String lastKey, String token) throws Exception;

    String getStoryFromServer(String handle, String lastKey, String token) throws  Exception;

    String getHashtagFeedFromServer(String handle, String lastKey, String token) throws  Exception;

    String getFollowingFromServer(String handle, String lastKey, String token) throws Exception;

    String getFollowersFromServer(String handle, String lastKey, String token) throws Exception;

    void sendNewStatus(NewStatusRequest request) throws  Exception;

    String followUser(SetFollowUserRequest request) throws Exception;

    String isProfileFollowingUser(IsFollowingRequest request) throws Exception;

    String updateProfilePicture(ProfilePictureRequest request) throws Exception;

    String signUserUp(SignUpRequest request) throws Exception;

    String getUserFromServer(String handle) throws Exception;

    String signInUser(SignInRequest request) throws Exception;

    String logOutUser(LogOutRequest request) throws Exception;
}
