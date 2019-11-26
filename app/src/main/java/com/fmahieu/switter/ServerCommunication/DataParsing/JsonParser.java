package com.fmahieu.switter.ServerCommunication.DataParsing;

import com.fmahieu.switter.ModelLayer.ApplicationLogic.DataParser;
import com.fmahieu.switter.ModelLayer.models.FollowUsers;
import com.fmahieu.switter.ModelLayer.models.StatusContentResult;
import com.fmahieu.switter.ModelLayer.models.FollowResult;
import com.fmahieu.switter.ModelLayer.models.SignInResult;
import com.fmahieu.switter.ModelLayer.models.httpModel.SignUpRequest;
import com.fmahieu.switter.ModelLayer.models.Status;
import com.fmahieu.switter.ModelLayer.models.User;
import com.fmahieu.switter.ModelLayer.models.UserResult;
import com.fmahieu.switter.ModelLayer.models.httpModel.JsonHolder;
import com.fmahieu.switter.ModelLayer.models.httpModel.IsFollowingResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonParser implements DataParser {
    private final String TAG = "__JsonParser";


    GsonBuilder mGsonBuilder;
    Gson gson;

    public JsonParser(){
        mGsonBuilder = new GsonBuilder();
        mGsonBuilder.setPrettyPrinting();
        gson = mGsonBuilder.create();
    }


    @Override
    public FollowUsers getListFollow() {
        String jsonString = JsonHolder.getInstance().getContent();
        return gson.fromJson(jsonString, FollowUsers.class);
    }

    @Override
    public String convertStatusToJson(Status status){
        return gson.toJson(status);
    }

    @Override
    public String convertUserToJson(User user) {


        return gson.toJson(user);
    }

    @Override
    public User convertJsonToUser(String json) {
        User result = gson.fromJson(json, User.class);
        return result;
    }

    @Override
    public boolean convertResultToBoolean() {
        IsFollowingResult result = gson.fromJson(JsonHolder.getInstance().getContent(),
                                                IsFollowingResult.class);
        return result.isFollowing;
    }

    @Override
    public UserResult fromJsonToUserResult(String json) {
        return gson.fromJson(json, UserResult.class);
    }

    public String fromSignUpRequestToJson(SignUpRequest request) {
        return gson.toJson(request, SignUpRequest.class);
    }

    public StatusContentResult fromJsonToListStatusResult(String request) {
        return gson.fromJson(request, StatusContentResult.class);
    }

    public FollowResult fromJsonToListUserResult(String requesst) {
        return gson.fromJson(requesst, FollowResult.class);
    }

    public String fromStatusToJson(Status status) {
        return gson.toJson(status, Status.class);
    }

    public Status fromJsonToStatus(String request){
        return gson.fromJson(request, Status.class);
    }

    @Override
    public SignInResult fromJsonToSignInResult(String json) {
        return gson.fromJson(json, SignInResult.class);
    }

    @Override
    public Object fromJson(String json, Class objectClass) throws Exception{
        return gson.fromJson(json, objectClass);
    }

    @Override
    public String toJson(Object o, Class objectClass) throws Exception {
        return gson.toJson(o, objectClass);
    }
}
