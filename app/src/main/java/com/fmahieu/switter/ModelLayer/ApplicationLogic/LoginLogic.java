package com.fmahieu.switter.ModelLayer.ApplicationLogic;

import android.content.Context;

import com.fmahieu.switter.ModelLayer.models.ConfirmRequest;
import com.fmahieu.switter.ModelLayer.models.LoginResult;
import com.fmahieu.switter.ModelLayer.models.SignInRequest;
import com.fmahieu.switter.ModelLayer.models.SignUpRequest;
import com.fmahieu.switter.ModelLayer.models.singleton.Profile;
import com.fmahieu.switter.ServerCommunication.AwsAuthentication;

public class LoginLogic {

    Profile mProfile = Profile.getUserInstance();
    AwsAuthentication auth = new AwsAuthentication();

    public LoginLogic(){}

    public void connectUser(SignInRequest request){
        auth.SignUserIn(request);
    }

    public void signUpUser(SignUpRequest request){
        auth.SignUserUp(request);
    }

    public void confirmCode(ConfirmRequest request){
        auth.confirmCode(request.getCode(), request.getUsername());
        if(!Profile.getUserInstance().needToConfirmSignUp()) {
            auth.SignUserIn(new SignInRequest(request.getPassword(), request.getUsername()));
        }
    }

    public void logUserOut(){
        mProfile.logOutUser();
        auth.signOut();
    }

    public void initializeAuth(Context context){
        auth.initializeAWS(context);
    }

    public void forgotPassword(String handle) {
        auth.forgotPassword(handle);
    }

    public void resetPassword(String newPassword, String confirmationCode) {
        auth.resetPassword(confirmationCode, newPassword);
    }
}
