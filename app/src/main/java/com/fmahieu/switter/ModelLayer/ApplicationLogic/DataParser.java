package com.fmahieu.switter.ModelLayer.ApplicationLogic;

import com.fmahieu.switter.ModelLayer.models.FollowUsers;
import com.fmahieu.switter.ModelLayer.models.SignInResult;
import com.fmahieu.switter.ModelLayer.models.Status;
import com.fmahieu.switter.ModelLayer.models.User;
import com.fmahieu.switter.ModelLayer.models.UserResult;

public interface DataParser {

    /**
     * parse the content of the singleton containing the data
     */
    public FollowUsers getListFollow();

    public String convertStatusToJson(Status status);

    public String convertUserToJson(User user);

    public User convertJsonToUser(String json);

    public boolean convertResultToBoolean();

    UserResult fromJsonToUserResult(String json);

    SignInResult fromJsonToSignInResult(String json);

    Object fromJson(String json, Class objectClass) throws Exception;

    String toJson(Object o, Class objectClass) throws Exception;

}
