package com.fmahieu.switter.ModelLayer.ApplicationLogic;

import android.util.Log;

import com.fmahieu.switter.ModelLayer.models.NewStatusRequest;
import com.fmahieu.switter.ModelLayer.models.Status;
import com.fmahieu.switter.ServerCommunication.ConnectionHttp;
import com.fmahieu.switter.ServerCommunication.ContentHttp;
import com.fmahieu.switter.ServerCommunication.HttpFacade;
import com.fmahieu.switter.ServerCommunication.NewStatusHttpRequest;

public class NewStatusLogic {
    private final String TAG = "__NewStatusLogic";

    public NewStatusLogic(){}

    public void sendNewStatus(NewStatusRequest request){
        try {
            HttpFacadeInterface mHttpFacade = new HttpFacade();
            mHttpFacade.sendNewStatus(request);
        }
        catch (Exception e){
            Log.e(TAG, "error when trying to change attachedPicture", e);
        }
    }
}
