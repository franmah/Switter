package com.fmahieu.switter.ModelLayer.models;

public class LoginResult {

    private String informationMessage;

    public LoginResult(){ this.informationMessage = null; }
    public LoginResult(String errorMessage) {
        this.informationMessage = errorMessage;
    }

    public String getInformationMessage() {
        return informationMessage;
    }

    public void setInformationMessage(String informationMessage) {
        this.informationMessage = informationMessage;
    }
}
