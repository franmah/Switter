package com.fmahieu.switter.ModelLayer.ApplicationLogic;

import android.util.Log;

import com.fmahieu.switter.ModelLayer.models.FollowResult;
import com.fmahieu.switter.ModelLayer.models.singleton.Profile;
import com.fmahieu.switter.ServerCommunication.DataParsing.JsonParser;
import com.fmahieu.switter.ServerCommunication.HttpFacade;

public class FollowLogic {
    private final String TAG = "__UpdateFollowLogic";

    private HttpFacadeInterface mHttpFacade = new HttpFacade();

    public FollowLogic() {
    }

    public FollowResult getFollowersNextPage(String handle, String lastKey) {
        Log.i(TAG, "getting Followers next page");
        try {
            String jsonResult = mHttpFacade.getFollowersFromServer(handle, lastKey,
                    Profile.getUserInstance().getAuthToken());
            return parseResult(jsonResult);
        }
        catch (ClassCastException e){
            Log.e(TAG, "GetFollowersNextPage: cast error when parsing: ", e);
            return new FollowResult(null, "Something went wrong");
        }
        catch (Exception e) {
            Log.e(TAG, "GetFollowersNextPage: error while getting followers for handle: " + handle +
                    ", for lastKey: " + lastKey, e);
            return new FollowResult(null, "network error");
        }

    }

    public FollowResult getFollowingNextPage(String handle, String lastKey) {
        Log.i(TAG, "getting Followers next page");

        try {
            String jsonResult = mHttpFacade.getFollowingFromServer(handle, lastKey,
                    Profile.getUserInstance().getAuthToken());
            return parseResult(jsonResult);
        }

        catch (ClassCastException e){
            Log.e(TAG, "GetFollowingsNextPage: cast error when parsing: ", e);
            return new FollowResult(null, "Something went wrong");
        }

        catch (Exception e) {
            Log.e(TAG, "GettignFollowingNextPage: error while getting following for handle: " + handle +
                    ", for lastKey: " + lastKey, e);
            return new FollowResult(null, "network error");
        }
    }

    private FollowResult parseResult(String jsonResult) throws Exception {
        DataParser parser = new JsonParser();
        return (FollowResult) parser.fromJson(jsonResult, FollowResult.class);
    }
}
