package com.fmahieu.switter.ServerCommunication;

import android.content.Context;
import android.util.Log;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.SignOutOptions;
import com.amazonaws.mobile.client.UserStateDetails;
import com.amazonaws.mobile.client.results.ForgotPasswordResult;
import com.amazonaws.mobile.client.results.SignInResult;
import com.amazonaws.mobile.client.results.SignUpResult;
import com.amazonaws.mobile.client.results.UserCodeDeliveryDetails;

import com.fmahieu.switter.ModelLayer.models.SignInRequest;
import com.fmahieu.switter.ModelLayer.models.httpModel.SignUpRequest;
import com.fmahieu.switter.ModelLayer.models.singleton.Profile;

import java.util.HashMap;
import java.util.Map;

public class AwsAuthentication {
    private static final String TAG = "__AwsAuthentication";

    public void SignUserUp(SignUpRequest request){


        final String username = request.getHandle();
        final String password = request.getPassword();
        final Map<String, String> attributes = new HashMap<>();
        attributes.put("email", request.getEmail());

        Log.i(TAG,"email: "+ request.getEmail());

        // Code from amplify
        AWSMobileClient.getInstance().signUp(username, password, attributes, null, new Callback<SignUpResult>() {

            @Override
            public void onResult(final SignUpResult signUpResult) {

                Log.i(TAG, "Sign-up callback state: " + signUpResult.getConfirmationState());

                if (!signUpResult.getConfirmationState()) {
                    final UserCodeDeliveryDetails details = signUpResult.getUserCodeDeliveryDetails();

                    Log.i(TAG, "Confirm sign-up with: " + details.getDestination());
                    Profile.getUserInstance().setNeedToConfirmSignUp(true);

                } else {

                    Log.i(TAG, "Sign-up done.");
                    Profile.getUserInstance().setLoggedIn(true);
                }
            }

            @Override
            public void onError(Exception e) {
                Profile.getUserInstance().setLoggedIn(false);
                Log.e(TAG, "Sign-up error", e);
            }
        });
    }

    public void confirmCode(String code, String username){
        AWSMobileClient.getInstance().confirmSignUp(username, code, new Callback<SignUpResult>() {
            @Override
            public void onResult(final SignUpResult signUpResult) {

                        Log.d(TAG, "Sign-up callback state: " + signUpResult.getConfirmationState());
                        if (!signUpResult.getConfirmationState()) {
                            final UserCodeDeliveryDetails details = signUpResult.getUserCodeDeliveryDetails();
                            Log.i(TAG, "Confirm sign-up with: " + details.getDestination());
                            Profile.getUserInstance().setNeedToConfirmSignUp(true);

                        } else {
                            Log.i(TAG, "Sign-up done.");
                            Profile.getUserInstance().setNeedToConfirmSignUp(false);
                        }
                    }


            @Override
            public void onError(Exception e) {
                Profile.getUserInstance().setNeedToConfirmSignUp(true);
                Log.e(TAG, "Confirm sign-up error", e);
            }
        });
    }

    public void SignUserIn(SignInRequest request) {

        String username = request.getHandle();
        String password = request.getPassword();

        AWSMobileClient.getInstance().signIn(username, password, null, new Callback<SignInResult>() {
            @Override
            public void onResult(final SignInResult signInResult) {
                Log.d(TAG, "Sign-in callback state: " + signInResult.getSignInState());
                switch (signInResult.getSignInState()) {
                    case DONE:
                        Log.i(TAG, "Sign-in done.");
                        Profile.getUserInstance().setNeedToConfirmSignUp(false);
                        Profile.getUserInstance().setLoggedIn(true);
                        break;
                    case SMS_MFA:
                        Log.i(TAG, "Please confirm sign-in with SMS.");
                        Profile.getUserInstance().setLoggedIn(false);
                        break;
                    case NEW_PASSWORD_REQUIRED:
                        Log.i(TAG, "Please confirm sign-in with new password.");
                        Profile.getUserInstance().setLoggedIn(false);
                        break;
                    default:
                        Log.i(TAG, "Unsupported sign-in confirmation: " + signInResult.getSignInState());
                        Profile.getUserInstance().setLoggedIn(false);
                        break;
                }

            }

            @Override
            public void onError(Exception e) {
                Log.e(TAG, "Sign-in error", e);
                Profile.getUserInstance().setLoggedIn(false);
            }
        });
    }

    public void signOut(){
        Log.i(TAG, "signing user out...");
        AWSMobileClient.getInstance().signOut(SignOutOptions.builder().signOutGlobally(true).build(), new Callback<Void>() {
            @Override
            public void onResult(final Void result) {
                Profile.getUserInstance().setLoggedIn(false);
                Log.d(TAG, "user has been signed-out");
            }

            @Override
            public void onError(Exception e) {
                Profile.getUserInstance().setLoggedIn(true);
                Log.e(TAG, "sign-out error: ", e);
            }
        });
    }

    public void initializeAWS(Context context){
        Log.i(TAG, "initializing AWS");
        AWSMobileClient.getInstance().initialize(context, new Callback<UserStateDetails>() {
            @Override
            public void onResult(UserStateDetails userStateDetails) {
                switch (userStateDetails.getUserState()){
                    case SIGNED_IN:
                        Log.i(TAG, "initializeAWS(): user signed in");
                        break;
                    case SIGNED_OUT:
                        Log.i(TAG, "initializeAWS(): user signed out");
                        break;
                    default:
                        Log.i(TAG, "initializeAWS(): default");
                        AWSMobileClient.getInstance().signOut();
                        break;
                }
            }

            @Override
            public void onError(Exception e) {
                Log.i(TAG, "initializeAWS(): error: " + e.toString());
            }
        });
    }

    public void forgotPassword(String username){

        AWSMobileClient.getInstance().forgotPassword(username, new Callback<ForgotPasswordResult>() {
            @Override
            public void onResult(final ForgotPasswordResult resultAWS) {
                Log.d(TAG, "forgot password state: " + resultAWS.getState());
                switch (resultAWS.getState()) {
                    case CONFIRMATION_CODE:
                        Log.i(TAG, "Confirmation code is sent to reset password");
                        Profile.getUserInstance().setNeedToConfirmSignUp(true);
                        break;
                    default:
                        Log.e(TAG, "un-supported forgot password state");
                        Profile.getUserInstance().setNeedToConfirmSignUp(false);
                        break;
                }
            }

            @Override
            public void onError(Exception e) {
                Profile.getUserInstance().setNeedToConfirmSignUp(false); //Confirmation hasn't worked
                Log.e(TAG, "forgot password error", e);
            }
        });

    }

    public void resetPassword(String code, String password){

        AWSMobileClient.getInstance().confirmForgotPassword(password, code, new Callback<ForgotPasswordResult>() {
            @Override
            public void onResult(final ForgotPasswordResult resultAWS) {

                Log.d(TAG, "forgot password state: " + resultAWS.getState());
                switch (resultAWS.getState()) {
                    case DONE:
                        Log.i(TAG, "Password changed successfully");
                        Profile.getUserInstance().setNeedToConfirmSignUp(false);
                        break;
                    default:
                        Log.e(TAG, "un-supported forgot password state");
                        Profile.getUserInstance().setNeedToConfirmSignUp(true);
                        break;
                }
            }

            @Override
            public void onError(Exception e) {
                Profile.getUserInstance().setNeedToConfirmSignUp(true);
                // confirmation hasn't worked, user is still supposed to confirm
                Log.e(TAG, "forgot password error", e);
            }
        });

    }
}

