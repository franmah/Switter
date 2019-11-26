package com.fmahieu.switter.ModelLayer.ApplicationLogic;

import android.util.Log;

import com.fmahieu.switter.ModelLayer.models.User;
import com.fmahieu.switter.ModelLayer.models.UserResult;
import com.fmahieu.switter.ServerCommunication.DataParsing.JsonParser;
import com.fmahieu.switter.ServerCommunication.HttpFacade;

public class UserLogic {
    private final String TAG = "__UserLogic";

    private HttpFacadeInterface mHttpFacade = new HttpFacade();

    public UserLogic(){}

    public UserResult getUserFromServer(String handle) {

        try{
            String jsonResult = mHttpFacade.getUserFromServer(handle);
            DataParser parser = new JsonParser();
            return (UserResult) parser.fromJson(jsonResult, UserResult.class);
        }
        catch (Exception e){
            Log.e(TAG, "Error response from server", e);
            return new UserResult(null, "something went wrong while trying to get user");
        }

    }

    public User getUserFromJson(String jsonString){
        DataParser parser = new JsonParser();
        return parser.convertJsonToUser(jsonString);
    }

    public String getJsonFromUser(User user) {
        DataParser parser = new JsonParser();
        return parser.convertUserToJson(user);
    }
}
