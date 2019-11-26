package com.fmahieu.switter.ModelLayer.models.httpModel;

public class LogOutRequest {

    public String handle;
    public String token;

    public String getHandle() {
        return handle;
    }

    public String getToken() {
        return token;
    }

    public LogOutRequest(String handle, String token) {
        this.handle = handle;
        this.token = token;
    }
}
