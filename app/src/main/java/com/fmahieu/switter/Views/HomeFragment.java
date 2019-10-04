package com.fmahieu.switter.Views;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
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

import com.fmahieu.switter.Presenters.ContentPresenter;
import com.fmahieu.switter.Presenters.HomePresenter;
import com.fmahieu.switter.R;



public class HomeFragment extends Fragment implements View.OnClickListener {
    private final String TAG = "__HomeFragment";

    private HomePresenter mHomePresenter = new HomePresenter();

    private Uri profilePicture;

    private ImageView mProfilePicture;
    private TextView mLogout;
    private TextView mSearch;
    private TextView mNewTweet;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);

        Log.i(TAG, "onCreate()");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView()");

        View view = inflater.inflate(R.layout.home_fragment, container, false);


        // Get user profile
        profilePicture = mHomePresenter.getProfilePicture();

        setUpViews(view);
        setUpModelInstances();
        getFragment();

        return view;
    }

    private void setUpViews(View view){
        mProfilePicture = view.findViewById(R.id.profilePicture_homeFragment_ImageView);
        mLogout = view.findViewById(R.id.logout_homeFragment);
        mSearch = view.findViewById(R.id.search_homeFragment);
        mNewTweet = view.findViewById(R.id.newTweet_homeFragment);

        mLogout.setOnClickListener(this);
        mSearch.setOnClickListener(this);
        mNewTweet.setOnClickListener(this);

        mProfilePicture.setImageURI(profilePicture);
    }

    private void setUpModelInstances(){
        // set up each instance to know which user's data to display
        mHomePresenter.setUpFeedUser();
        mHomePresenter.setUpStoryUser();
        mHomePresenter.setUpFollowingUser();
        mHomePresenter.setUpFollowingUser();
    }


    @Override
    public void onClick(View v) {
        Intent intent;

        switch(v.getId()){
            case R.id.logout_homeFragment:
                updateSuperFragment(); // will tell MainActivity to switch to login fragment
                break;
            case R.id.search_homeFragment:
                intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
                break;
            case R.id.newTweet_homeFragment:
                intent = new Intent(getActivity(), NewStatusActivity.class);
                startActivity(intent);
                break;
        }
    }

    public void updateSuperFragment(){
        // is user logout MainActivity will switch to LoginFragment
        Log.i(TAG, "requesting MainActivity to switch fragment");

        Activity mainActivityInstance = getActivity();
        if(mainActivityInstance instanceof MainActivity){
            ((MainActivity) mainActivityInstance).getFragment();
        }
    }


    private void getFragment(){

        // get contentFragment
        FragmentManager fragmentManager = getChildFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragmentContainer_homeFragment_FrameLayout);

        if(fragment == null){
            fragment = new ContentFragment();
            fragmentManager.beginTransaction().add(R.id.fragmentContainer_homeFragment_FrameLayout, fragment).commit();
        }
        else{
            fragmentManager.beginTransaction().replace(R.id.fragmentContainer_homeFragment_FrameLayout, fragment).commit();
        }
    }
}
