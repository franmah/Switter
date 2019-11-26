package com.fmahieu.switter.ServerCommunication;

import android.util.Log;

public class UserHttp {
    private final String TAG = "__UserHttp";

    public String getUserFromServer(String handle) throws Exception {
        String url = "user/" + handle + "/";
        Log.i(TAG, "sending a request for a user to: " + url);

        ConnectionHttp connectionHttp = new ConnectionHttp();
        return connectionHttp.sendRequest(url, "GET", null, null);

    }
}
