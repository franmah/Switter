package com.fmahieu.switter.ModelLayer.ApplicationLogic;

import android.content.Context;
import android.util.Log;

import com.fmahieu.switter.ModelLayer.models.ConfirmRequest;
import com.fmahieu.switter.ModelLayer.models.LinkProfilePicture;
import com.fmahieu.switter.ModelLayer.models.MessageResult;
import com.fmahieu.switter.ModelLayer.models.SignInRequest;
import com.fmahieu.switter.ModelLayer.models.SignInResult;
import com.fmahieu.switter.ModelLayer.models.httpModel.LogOutRequest;
import com.fmahieu.switter.ModelLayer.models.httpModel.SignUpRequest;
import com.fmahieu.switter.ModelLayer.models.User;
import com.fmahieu.switter.ModelLayer.models.httpModel.SignUpResult;
import com.fmahieu.switter.ModelLayer.models.singleton.Profile;
import com.fmahieu.switter.ServerCommunication.AwsAuthentication;
import com.fmahieu.switter.ServerCommunication.DataParsing.JsonParser;
import com.fmahieu.switter.ServerCommunication.HttpFacade;
import com.fmahieu.switter.ServerCommunication.ProfileHttp;

public class LoginLogic {
    private final String TAG = "__LoginLogic";

    AwsAuthentication auth = new AwsAuthentication();
    HttpFacadeInterface mHttpFacade = new HttpFacade();
    DataParser parser = new JsonParser();

    public LoginLogic(){}

    public String connectUser(SignInRequest request){
        try {
            String jsonResult = mHttpFacade.signInUser(request);
            SignInResult result = parser.fromJsonToSignInResult(jsonResult);

            if(result == null){
                Log.i(TAG, "connectUser: result came back null");
                return "network error";
            }
            else if(result.getError() != null){
                return result.getError();
            }
            else{
                Profile profile = Profile.getUserInstance();
                profile.setUpProfileWithUser(result.getUser());
                profile.setAuthToken(result.getToken());
                profile.setLoggedIn(true);
                return null;
            }
        }
        catch (Exception e){
            Log.e(TAG, "error while trying to connect user (error from lambda server)", e);
            return "network error";
        }
    }

    public String signUpUser(SignUpRequest request){

        try{
            String jsonResult = mHttpFacade.signUserUp(request);
            SignUpResult result = (SignUpResult) parser.fromJson(jsonResult, SignUpResult.class);

            if(request == null){
                Log.i(TAG, "signUpUser: result came back null");
                return "network error";
            }
            else if(result.getError() != null){
                Log.i(TAG, "signUpUser: result came back with an error: " + result.getError());
                return result.getError();
            }
            else{
                // fill Profile instance
                Profile profile = Profile.getUserInstance();
                profile.setHandle(request.getHandle());
                profile.setFirstName(request.getFirstName());
                profile.setLastName(request.getLastName());
                profile.setProfilePictureLink(new LinkProfilePicture(request.getHandle()));
                profile.setAuthToken(result.getToken());
                profile.setLoggedIn(true);

                return null;
            }

        }
        catch (ClassCastException e){
            Log.e(TAG, "signUpUser: the result is not of type 'SignUpResult", e);
            return "Something went wrong";
        }
        catch (Exception e){
            Log.e(TAG, "signUpUser: error: ", e);
            return "Something went wrong";
        }

    }

    public void confirmCode(ConfirmRequest request){
        // TODO: uncomment auth./...
        //auth.confirmCode(request.getCode(), request.getHandle());
        if(!Profile.getUserInstance().needToConfirmSignUp()) {
            //auth.SignUserIn(new SignInRequest(request.getPassword(), request.getHandle()));
        }
    }

    public MessageResult logUserOut(){
        try{
            Profile profile = Profile.getUserInstance();
            String jsonResult = mHttpFacade.logOutUser(new LogOutRequest(
                    profile.getHandle(), profile.getAuthToken()));

            MessageResult result = (MessageResult) parser.fromJson(jsonResult,
                                                            MessageResult.class);
            if(result == null){
                Log.i(TAG, "logUserOut: result came back null");
                return new MessageResult(null, "something went wrong");
            }

            // Log user out
            Profile.getUserInstance().setLoggedIn(false);

            return result;
        }
        catch (Exception e){
            Log.e(TAG, "error: ", e);
            return new MessageResult(null, "network error");
        }
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
