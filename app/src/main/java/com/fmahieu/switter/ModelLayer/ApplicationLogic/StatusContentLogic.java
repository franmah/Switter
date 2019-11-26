package com.fmahieu.switter.ModelLayer.ApplicationLogic;

import android.util.Log;

import com.fmahieu.switter.ModelLayer.models.StatusContentResult;
import com.fmahieu.switter.ModelLayer.models.Status;
import com.fmahieu.switter.ModelLayer.models.singleton.Profile;
import com.fmahieu.switter.ServerCommunication.DataParsing.JsonParser;
import com.fmahieu.switter.ServerCommunication.HttpFacade;

public class StatusContentLogic {
    private final String TAG = "__UpdateStatusContentLogic";
    private HttpFacadeInterface mHttpFacade = new HttpFacade();
    private DataParser parser = new JsonParser();

    public StatusContentLogic(){}

    /**
     * get the first page of the feed
     */
    public StatusContentResult getFeed(String handle, String lastKey) {
        try {
            String jsonResult =  mHttpFacade.getFeedFromServer(handle, lastKey,
                                        Profile.getUserInstance().getAuthToken());
            return parseResult(jsonResult);
        }
        catch (ClassCastException e){
            Log.e(TAG, "GetFeed: cast error: " , e);
            return new StatusContentResult(null, "something went wrong");
        }
        catch (Exception e){
            Log.e(TAG, "error while getting feed: handle:" + handle +
                    ", lastKey: " + lastKey, e);
            return new StatusContentResult(null, "network error");
        }
    }

    public StatusContentResult getStory(String handle, String lastKey){
        try{
            String jsonResult = mHttpFacade.getStoryFromServer(handle, lastKey,
                                    Profile.getUserInstance().getAuthToken());
            return parseResult(jsonResult);
        }
        catch (ClassCastException e){
            Log.e(TAG, "GetStory: cast error: " , e);
            return new StatusContentResult(null, "something went wrong");
        }
        catch (Exception e){
            Log.e(TAG, "error while getting feed: handle:" + handle +
                    ", lastKey: " + lastKey, e);
            return new StatusContentResult(null, "network error");
        }
    }

    public StatusContentResult getHashtagFeed(String hashtag, String lastKey){
        try{
            String jsonResult = mHttpFacade.getHashtagFeedFromServer(hashtag, lastKey,
                    Profile.getUserInstance().getAuthToken());
            return parseResult(jsonResult);
        }
        catch (ClassCastException e){
            Log.e(TAG, "GetHashtagFeed: cast error: " , e);
            return new StatusContentResult(null, "something went wrong");
        }
        catch (Exception e){
            Log.e(TAG, "error while getting feed: handle:" + hashtag +
                    ", lastKey: " + lastKey, e);
            return new StatusContentResult(null, "network error");
        }
    }

    public String getJsonFromStatus(Status status){
        try {
            return parser.toJson(status, Status.class);
        }
        catch (Exception e){
            Log.e(TAG, "Error while converting status to json", e);
            return null;
        }
    }

    private StatusContentResult parseResult(String jsonResult) throws Exception{
        JsonParser parser = new JsonParser();
        return (StatusContentResult)  parser.fromJson(jsonResult, StatusContentResult.class);
    }

    public Status getStatusFromJson(String jsonStatus) {
        try {
            return (Status) parser.fromJson(jsonStatus, Status.class);
        }
        catch (Exception e){
            Log.e(TAG, "Error while converting json to status", e);
            return null;
        }
    }
}
