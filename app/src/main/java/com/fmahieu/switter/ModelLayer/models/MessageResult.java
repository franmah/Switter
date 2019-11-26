package com.fmahieu.switter.ModelLayer.models;

public class MessageResult {

    public String message;
    public String error;

    public String getError() {
        return error;
    }

    public MessageResult(String message, String error) {
        this.message = message;
        this.error = error;
    }
}
