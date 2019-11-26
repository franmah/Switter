package com.fmahieu.switter.ModelLayer.models.httpModel;

public class SignUpResult {

    public String token;
    public String error;

    /**
     * Only for data parser
     */
    public SignUpResult(){}

    public String getToken() {
        return token;
    }

    public String getError() {
        return error;
    }
}
