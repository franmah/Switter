package com.fmahieu.switter.Views;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fmahieu.switter.R;

public class HomeFragment extends Fragment implements View.OnClickListener {
    private final String TAG = "HomeFragment";

    private ImageView mProfilePicture;
    private TextView mLogout;
    private TextView mSearch;
    private TextView mNewTweet;

    private TextView mFeed;
    private TextView mStory;
    private TextView mFollowing;
    private  TextView mFollowers;

    private boolean showFeed = false;
    private boolean showStory = false;
    private boolean showFollowing = false;
    private  boolean showFollowers = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);

        Log.i(TAG, "onCreate()");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView()");

        View view = inflater.inflate(R.layout.signup_fragment, container, false);

        setUpViews(view);

        return view;
    }

    private void setUpViews(View view){
        mProfilePicture = view.findViewById(R.id.profilePicture_homeFragment_ImageView);
        mLogout = view.findViewById(R.id.logout_homeFragment);
        mSearch = view.findViewById(R.id.search_homeFragment);
        mNewTweet = view.findViewById(R.id.newTweet_homeFragment);

        mFeed = view.findViewById(R.id.feed_homeFragment_TextView);
        mStory = view.findViewById(R.id.story_homeFragment_TextView);
        mFollowing = view.findViewById(R.id.following_homeFragment_TextView);
        mFollowers = view.findViewById(R.id.followers_homeFragment_TextView);

        mLogout.setOnClickListener(this);
        mSearch.setOnClickListener(this);
        mNewTweet.setOnClickListener(this);

        mFeed.setOnClickListener(this);
        mStory.setOnClickListener(this);
        mFollowing.setOnClickListener(this);
        mFollowers.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.logout_homeFragment:
// TODO: call presenter to erase value
                break;
            case R.id.search_homeFragment:
// TODO: get to search activity
                break;
            case R.id.newTweet_homeFragment:
// TODO : get to  new tweet activity
                break;
            case R.id.feed_homeFragment_TextView:
                setAllShowTabFalse();
                showFeed = true;
                getFragment();
                break;
            case R.id.story_homeFragment_TextView:
                setAllShowTabFalse();
                showStory = true;
                getFragment();
                break;
            case  R.id.following_homeFragment_TextView:


        }
    }

    private void setAllShowTabFalse(){
        showFeed = false;
        showStory = false;
        showFollowing = false;
        showFollowers = false;
    }
}
