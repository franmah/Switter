package com.fmahieu.switter.Presenters;

import com.fmahieu.switter.ModelLayer.ApplicationLogic.LoginLogic;
import com.fmahieu.switter.ModelLayer.models.ConfirmRequest;
import com.fmahieu.switter.ModelLayer.models.EncodedProfilePicture;
import com.fmahieu.switter.ModelLayer.models.SignInRequest;
import com.fmahieu.switter.ModelLayer.models.httpModel.SignUpRequest;

public class
LoginPresenter {
    private final String TAG = "__LoginPresenter";

    private LoginLogic mLoginLogic = new LoginLogic();


    public LoginPresenter(){}

    public String signUserUp(EncodedProfilePicture image, String firstName, String lastName, String handle,
                             String password, String email){

        SignUpRequest request = new SignUpRequest(image, firstName, lastName, handle, password, email);
        return mLoginLogic.signUpUser(request);
    }

    /**
     * Send request to logic layer and receive a result back from the server.
     * @param handle
     * @param password
     * @return if null: everything went fine, else: return an error message to be displayed
     */
    public String connectUser(String handle, String password){
        SignInRequest signInRequest = new SignInRequest(password, handle);
        return mLoginLogic.connectUser(signInRequest);
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
