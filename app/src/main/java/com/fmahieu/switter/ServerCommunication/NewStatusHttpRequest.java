package com.fmahieu.switter.ServerCommunication;

import com.fmahieu.switter.ModelLayer.ApplicationLogic.DataParser;
import com.fmahieu.switter.ModelLayer.models.NewStatusRequest;
import com.fmahieu.switter.ServerCommunication.DataParsing.JsonParser;

public class NewStatusHttpRequest {

    private final String TAG = "__NewStatusHttpRequest";

    public void sendNewStatus(NewStatusRequest request) throws Exception{
        String url = "/status";
        String method = "POST";

        ConnectionHttp connectionHttp = new ConnectionHttp();
        connectionHttp.sendRequest(url, method, createRequestBody(request), null);

    }

    private String createRequestBody(NewStatusRequest request) throws  Exception{
        DataParser parser = new JsonParser();
        return parser.toJson(request, NewStatusRequest.class);
    }
}
