package com.fmahieu.switter.Views;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.UserStateDetails;
import com.amazonaws.mobile.client.UserStateListener;
import com.fmahieu.switter.Presenters.MainActivityPresenter;
import com.fmahieu.switter.R;

import static com.amazonaws.mobile.client.UserState.SIGNED_IN;

public class MainActivity extends AppCompatActivity{
    private final String TAG = "__MainActivity";

    private MainActivityPresenter mMainActivityPresenter = new MainActivityPresenter();
    private FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "MainActivity started");

        setContentView(R.layout.main_activity);

        //initializeAWS();
        mMainActivityPresenter.initializeAuth(this);

        //createAwsListener();
        getFragment();
    }

    public void getFragment(){
        Log.i(TAG, "getting fragment");

        boolean isUserLoggedIn = mMainActivityPresenter.isUserLoggedIn();

        Fragment fragment = fragmentManager.findFragmentById(R.id.fragmentContainer_mainActivity_FrameLayout);

        Log.i(TAG, "FLAG2");
        if(fragment == null){
            if(isUserLoggedIn){
                fragment = new HomeFragment();
            }
            else{
                fragment = new LoginFragment();
            }

            fragmentManager.beginTransaction().add(R.id.fragmentContainer_mainActivity_FrameLayout, fragment).commit();
        }
        else{
            if(isUserLoggedIn){
                fragment = new HomeFragment();
            }
            else{
                fragment = new LoginFragment();
            }

            fragmentManager.beginTransaction().replace(R.id.fragmentContainer_mainActivity_FrameLayout, fragment).commit();
        }

    }

    public void initializeAWS(){
        Log.i(TAG, "initializing AWS");
        AWSMobileClient.getInstance().initialize(getApplicationContext(), new Callback<UserStateDetails>() {
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

    private void createAwsListener(){
        AWSMobileClient.getInstance().addUserStateListener(new UserStateListener() {
            @Override
            public void onUserStateChanged(UserStateDetails userStateDetails) {
                switch (userStateDetails.getUserState()){
                    case SIGNED_OUT:
                        Log.i(TAG, "user signed out");
                        break;
                    case SIGNED_OUT_USER_POOLS_TOKENS_INVALID:
                        Log.i(TAG, "need to login again.");
                        break;
                    default:
                        Log.i(TAG, "unsupported");
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        getFragment(); // will be called when returning to the activity.
    }
}
