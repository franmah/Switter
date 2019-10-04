package com.fmahieu.switter.Views;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fmahieu.switter.R;

public class LoginFragment extends Fragment implements View.OnClickListener {

    private final String TAG = "__LoginFragment";

    private ImageView mLogoImageView;
    private TextView mSignInTextView;
    private TextView mSignUpTextView;

    private boolean isSignInClicked = true;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate()");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView()");

        View view = inflater.inflate(R.layout.login_fragment, container, false);

        setUpViews(view);
        getFragment();

        return view;
    }

    private void setUpViews(View view){
        Log.i(TAG, "Setting up views");

        mLogoImageView = (ImageView) view.findViewById(R.id.logo_loginFragment_ImageView);
        mSignInTextView = (TextView) view.findViewById(R.id.signIn_loginFragment_textView);
        mSignUpTextView = (TextView) view.findViewById(R.id.signUp_loginFragment_textView);

        // set logo image
        // TODO: replace image by actual logo
        mLogoImageView.setImageResource(R.mipmap.ic_logo_twitter_final);

        // Set listener:
        mSignInTextView.setOnClickListener(this);
        mSignUpTextView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Log.i(TAG, "a textView has been clicked on");

        switch (v.getId()){

            case R.id.signIn_loginFragment_textView:
                isSignInClicked = true;
                getFragment();
                break;

            case R.id.signUp_loginFragment_textView:
                isSignInClicked = false;
                getFragment();
                break;
        }

    }

    /**
     * Update fragment to show, either the signUp or signIn fragment.
     */
    private void getFragment(){
        Log.i(TAG, "getting new fragment");

        FragmentManager fragmentManager = getChildFragmentManager();

        Fragment fragment = fragmentManager.findFragmentById(R.id.fragmentContainer_loginFragment_FrameLayout);

        if(fragment == null){ // show signInFragment by default
            fragment = new SignInFragment();
            fragmentManager.beginTransaction().add(R.id.fragmentContainer_loginFragment_FrameLayout, fragment).commit();
        }
        else{
            fragment = isSignInClicked ? new SignInFragment() : new SignUpFragment();
            fragmentManager.beginTransaction().replace(R.id.fragmentContainer_loginFragment_FrameLayout, fragment).commit();
        }
    }

    /**
     * Update the fragment shown in the main activity
     * Once the user login this function tells the main activity to re-check which fragment to show.
     */
    public void changeSuperFragment(){
        Log.i(TAG, "requesting MainActivity to switch fragment");
        Activity mainActivityInstance = getActivity();
        if(mainActivityInstance instanceof MainActivity){
            ((MainActivity) mainActivityInstance).getFragment();
        }
    }
}
