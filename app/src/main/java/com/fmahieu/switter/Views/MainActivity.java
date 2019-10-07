package com.fmahieu.switter.Views;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.fmahieu.switter.Presenters.MainActivityPresenter;
import com.fmahieu.switter.R;

public class MainActivity extends AppCompatActivity{
    private final String TAG = "__MainActivity";

    private MainActivityPresenter mMainActivityPresenter = new MainActivityPresenter();
    private FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "MainActivity started");

        setContentView(R.layout.main_activity);
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

    @Override
    protected void onResume() {
        super.onResume();

        getFragment(); // will be called when returning to the activity.
    }
}
