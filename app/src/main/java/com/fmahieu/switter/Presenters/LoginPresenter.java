package com.fmahieu.switter.Presenters;


import android.net.Uri;
import com.fmahieu.switter.ModelLayer.ApplicationLogic.LoginLogic;
import com.fmahieu.switter.ModelLayer.models.ConfirmRequest;
import com.fmahieu.switter.ModelLayer.models.Handle;
import com.fmahieu.switter.ModelLayer.models.SignInRequest;
import com.fmahieu.switter.ModelLayer.models.SignUpRequest;
import com.fmahieu.switter.ModelLayer.models.singleton.Profile;

public class LoginPresenter {

    private LoginLogic mLoginLogic = new LoginLogic();

    // TODO: REMOVE
    private Profile profile = Profile.getUserInstance();

    public LoginPresenter(){}

    public void signUserUp(Uri uri,String firstName, String lastName, String handle, String password,
                           String email){

        SignUpRequest request = new SignUpRequest(uri, firstName, lastName, handle, password, email);
        mLoginLogic.signUpUser(request);

        profile.setFirstName(firstName);
        profile.setLastName(lastName);
        profile.setHandle(new Handle(handle));
        profile.setNumFollowers(0);
        profile.setNumFollowing(0);
    }

    // TODO return a response
    public void connectUser(String handle, String password){

        SignInRequest signInRequest = new SignInRequest(password, handle);
        mLoginLogic.connectUser(signInRequest);
        return;
    }

    public void confirmCode(String code, String handle, String password) {
        mLoginLogic.confirmCode(new ConfirmRequest(code, handle, password));
    }

    public void forgotPassword(String handle) {
        mLoginLogic.forgotPassword(handle);
    }

    public void resetPassword(String newPassword, String confirmationCode) {
        mLoginLogic.resetPassword(newPassword, confirmationCode);
    }
}
