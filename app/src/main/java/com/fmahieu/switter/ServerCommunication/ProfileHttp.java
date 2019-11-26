package com.fmahieu.switter.ServerCommunication;

import android.util.Log;

import com.fmahieu.switter.ModelLayer.ApplicationLogic.DataParser;
import com.fmahieu.switter.ModelLayer.models.ProfilePictureRequest;
import com.fmahieu.switter.ModelLayer.models.SignInRequest;
import com.fmahieu.switter.ModelLayer.models.SetFollowUserRequest;
import com.fmahieu.switter.ModelLayer.models.httpModel.IsFollowingRequest;
import com.fmahieu.switter.ModelLayer.models.httpModel.LogOutRequest;
import com.fmahieu.switter.ModelLayer.models.httpModel.SignUpRequest;
import com.fmahieu.switter.ServerCommunication.DataParsing.JsonParser;

import java.util.Map;
import java.util.TreeMap;

public class ProfileHttp {
    private final String TAG = "__ProfileHttp";

    private ConnectionHttp connectionHttp = new ConnectionHttp();

    public String followUser(SetFollowUserRequest request) throws Exception{
        String url = "follow/" + request.getProfile() + "/" + request.getUser() +
                "?token=" + request.getToken() + "&follow=";
        if(request.isFollow()){
            url = url + "true";
        }
        else{
            url = url + "false";
        }

        return connectionHttp.sendRequest(url, "POST", null, null);
    }

    public String isProfileFollowingUser(IsFollowingRequest request) throws Exception{
        String url = "isfollowing/" + request.getProfile() + "/" + request.getUser() +
                "?token=" + request.getToken();
        Log.i(TAG, "isfollowing: sending request: " + url);

        return connectionHttp.sendRequest(url, "GET", null, null);
    }

    public String updateProfilePicture(ProfilePictureRequest request) throws Exception{
        String url = "profile/updatepicture";

        // create request body
        DataParser parser = new JsonParser();
        String body = parser.toJson(request, ProfilePictureRequest.class);
        Log.i(TAG, "updating profile attachedPicture with: " + url);

        return connectionHttp.sendRequest(url, "POST", body,null);
    }

    public String signUpUser(SignUpRequest request) throws Exception{
        String url = "profile/signup/";

        // Get request body content
        JsonParser parser = new JsonParser();
        String jsonBody = parser.toJson(request, SignUpRequest.class);

        return connectionHttp.sendRequest(url, "POST", jsonBody, null);
    }

    public String signInUser(SignInRequest request) throws Exception{
        String url = "profile/signin/";

        // create request headers (use headers to avoid displaying the password in clear in the url)
        Map<String, String> headers = new TreeMap<>();
        headers.put("handle", request.getHandle());
        headers.put("password", request.getPassword());

        return connectionHttp.sendRequest(url, "GET",null, headers);
    }

    public String logOutUser(LogOutRequest request) throws Exception{
        String url = "profile/loguserout/" + request.getHandle() + "?token=" + request.getToken();
        return connectionHttp.sendRequest(url, "POST", null, null);
    }
}
