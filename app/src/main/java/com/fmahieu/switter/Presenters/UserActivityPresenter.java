package com.fmahieu.switter.Presenters;

import com.fmahieu.switter.ModelLayer.ApplicationLogic.ProfileLogic;
import com.fmahieu.switter.ModelLayer.ApplicationLogic.UserLogic;
import com.fmahieu.switter.ModelLayer.models.MessageResult;
import com.fmahieu.switter.ModelLayer.models.User;
import com.fmahieu.switter.ModelLayer.models.UserResult;
import com.fmahieu.switter.ModelLayer.models.httpModel.IsFollowingResult;

public class UserActivityPresenter {

    private ProfileLogic profileLogic;
    private UserLogic userLogic;

    public UserActivityPresenter(){
        userLogic = new UserLogic();
        profileLogic = new ProfileLogic();
    }

    public UserResult getUserFromServer(String handle){
        return userLogic.getUserFromServer(handle);
    }

    public MessageResult followUser(String handle){
        return profileLogic.followUser(handle);
    }

    public MessageResult unFollowUser(String handle){
        return profileLogic.unfollowUser(handle);

    }

    public IsFollowingResult isProfileFollowingUser(String handle){
        return profileLogic.isProfileFollowingUser(handle);
    }

    public User getUserFromJson(String jsonString){
        return userLogic.getUserFromJson(jsonString);
    }

    public String getJsonFromUser(User user){
        return userLogic.getJsonFromUser(user);
    }
}
